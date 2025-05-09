package agents.agentnabytku;

import Enums.*;
import OSPABA.*;
import entities.*;
import simulation.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.PriorityQueue;

//meta! id="9"
public class ManagerNabytku extends OSPABA.Manager
{
	private final QueueNonProcessed queueNonProcessed;
	private final QueueStaining queueStaining;
	private final QueueAssembly queueAssembly;
	private final QueueMontage queueMontage;
	private final PriorityQueue<WorkPlace> freeWorkplaces;

	public QueueNonProcessed getQueueNonProcessed() {
		return queueNonProcessed;
	}

	public QueueStaining getQueueStaining() {
		return queueStaining;
	}

	public QueueAssembly getQueueAssembly() {
		return queueAssembly;
	}

	public QueueMontage getQueueMontage() {
		return queueMontage;
	}

	public QueuePainting getQueuePainting() {
		return queuePainting;
	}

	private final QueuePainting queuePainting;
	public ManagerNabytku(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) mySim;
		queueNonProcessed = new QueueNonProcessed(mySimulation);
		queueStaining = new QueueStaining(mySimulation);
		queueAssembly = new QueueAssembly(mySimulation);
		queueMontage = new QueueMontage(mySimulation);
		queuePainting = new QueuePainting(mySimulation);
		freeWorkplaces = new PriorityQueue<>(Comparator.comparingInt(WorkPlace::getId));
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
		MySimulation mySimulation = (MySimulation)mySim();
		queueNonProcessed.clear();
		queueStaining.clear();
		queueAssembly.clear();
		queueMontage.clear();
		queuePainting.clear();
		freeWorkplaces.clear();
		freeWorkplaces.addAll(mySimulation.getWorkPlacesArrayList());
		for (WorkPlace workPlace : freeWorkplaces) {
			workPlace.clear();
		}

	}
	//meta! sender="AgentModelu", id="24", type="Notice"
	public void processInit(MessageForm message) {
	}

	public WorkPlace tryAssignFreeWorkplace() {
		WorkPlace wp = freeWorkplaces.poll();
		if (wp != null) {
			wp.setState(WorkPlaceStateValues.ASSIGNED.getValue());
		}
		return wp;
	}

	public void releaseWorkPlace(WorkPlace wp) {
		if (wp != null) {
			wp.setState(WorkPlaceStateValues.NOT_WORKING.getValue());
			wp.clear();
			freeWorkplaces.add(wp);
		}
	}
	//Pracovne miesta
//meta! sender="AgentModelu", id="81", type="Notice"
	public void processNoticeSpracujObjednavku(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) _mySim;
		msg.getOrder().setState(OrderStateValues.PROCESSING_ORDER.getValue());
		for (Furniture furniture : msg.getOrder().getFurnitureList()) {
			MyMessage furnitureMsg = new MyMessage(mySimulation);
			furnitureMsg.setOrder(msg.getOrder());
			furnitureMsg.setFurniture(furniture);
			furnitureMsg.setWorkPlace(null);
			furnitureMsg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_NEW.getValue());
			furnitureMsg.setWorkerForCutting(null);
			furnitureMsg.setWorkerForAssembly(null);
			furnitureMsg.setWorkerForMontage(null);
			this.queueNonProcessed.addLast(furnitureMsg);
			//System.out.println(queueNonProcessed.getQueue());
		}
		//System.out.println(queueNonProcessed.getQueue().size());
		this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		this.checkAllItemsQueue(queueStaining, OperationType.STAINING);
		this.checkAllItemsQueue(queuePainting, OperationType.PAINTING);
		this.checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);
		this.checkAllItemsQueue(queueAssembly, OperationType.ASSEMBLY);
	}

	//meta! sender="AgentPracovnikov", id="168", type="Response"
	public void processRVyberPracovnikaRezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		Optional<MyMessage> opt = queueNonProcessed.getQueue().stream()
				.filter(q -> q.equals(msg))
				.findFirst();

		if (opt.isPresent()) {
			MyMessage found = opt.get();
			found.setWorkerForCutting(msg.getWorkerForCutting());

			if (found.getWorkPlace() == null) {
				WorkPlace wp = tryAssignFreeWorkplace();
				if (wp != null) {
					found.setWorkPlace(wp);
					found.getFurniture().setWorkPlace(wp);
				}
			}

			if (found.getWorkerForCutting() != null && found.getWorkPlace() != null) {
				handleCompleteCuttingPreparation(found, queueNonProcessed);
			}
		}
	}


	private void handleCompleteCuttingPreparation(MyMessage msg, Queue queue) {

		msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
		msg.getFurniture().setWorkPlace(msg.getWorkPlace());
		msg.getWorkPlace().setFurniture(msg.getFurniture());

		boolean removed = queue.removeIf(m -> m.equals(msg));

		if (msg.getWorkerForCutting().getCurrentWorkPlace() != null) {
			msg.setCode(Mc.rPresunDoSkladu);
			msg.setAddressee(mySim().findAgent(Id.agentPohybu));
			msg.getWorkerForCutting().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue(), mySim().currentTime());
		} else {

			msg.setCode(Mc.rPripravVSklade);
			msg.setAddressee(mySim().findAgent(Id.agentSkladu));
			msg.getWorkerForCutting().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue(), mySim().currentTime());
		}

		request(msg);
	}


	private void handleCompletePreparation(MyMessage msg, Queue queue, OperationType type) {

		queue.removeIf(m -> m.equals(msg));

		WorkPlace currentWorkerWorkPlace = switch (type) {
			case STAINING -> msg.getWorkerForStaining().getCurrentWorkPlace();
			case PAINTING -> msg.getWorkerForPainting().getCurrentWorkPlace();
			case ASSEMBLY -> msg.getWorkerForAssembly().getCurrentWorkPlace();
			case MONTAGE -> msg.getWorkerForMontage().getCurrentWorkPlace();
			default -> throw new IllegalArgumentException("Unsupported type in handleCompletePreparation");
		};

		if (currentWorkerWorkPlace != null && currentWorkerWorkPlace.equals(msg.getWorkPlace())) {
			msg.setCode(switch (type) {
				case STAINING -> Mc.rUrobMorenie;
				case PAINTING -> Mc.rUrobLakovanie;
				case ASSEMBLY -> Mc.rUrobSkladanie;
				case MONTAGE -> Mc.rUrobMontaz;
				default -> throw new IllegalArgumentException("Unsupported type in rUrob");
			});
			msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
		} else if (currentWorkerWorkPlace == null) {
			msg.setCode(Mc.rPresunZoSkladu);
			msg.setAddressee(mySim().findAgent(Id.agentPohybu));
			setWorkerState(msg, type, WorkerBussyState.MOVING_FROM_STORAGE);
		} else {
			msg.setCode(Mc.rPresunNaPracovisko);
			msg.setAddressee(mySim().findAgent(Id.agentPohybu));
			setWorkerState(msg, type, WorkerBussyState.MOVE_TO_WORKPLACE);
		}

		request(msg);
	}


	//Sklad
	//meta! sender="AgentSkladu", id="324", type="Response"
	public void processRPripravVSklade(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPresunZoSkladu);
		msg.getWorkerForCutting().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue(), mySim().currentTime());
		msg.setAddressee(_mySim.findAgent(Id.agentPohybu));
		request(msg);
	}




	private void checkAllItemsQueue(Queue queue, OperationType type) {
		// System.out.println("Kontrola fronty typu " + type + " | Počet položiek: " + queue.getQueue().size());

		int availableResources = getAvailableResourcesCount(type);
		int processed = 0;
		Iterator<MyMessage> iterator = queue.getQueue().iterator();
		while (iterator.hasNext() && processed < availableResources) {
			MyMessage msgFromQueue = iterator.next();

			boolean hasWorker = switch (type) {
				case CUTTING -> msgFromQueue.getWorkerForCutting() != null;
				case STAINING -> msgFromQueue.getWorkerForStaining() != null;
				case PAINTING -> msgFromQueue.getWorkerForPainting() != null;
				case ASSEMBLY -> msgFromQueue.getWorkerForAssembly() != null;
				case MONTAGE -> msgFromQueue.getWorkerForMontage() != null;
			};

			boolean hasWorkPlace = msgFromQueue.getWorkPlace() != null;

			if (hasWorker && hasWorkPlace) {
				MyMessage newMsg = new MyMessage(msgFromQueue);
				iterator.remove();
				newMsg.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());

				if (type == OperationType.CUTTING) {
					newMsg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
					newMsg.getFurniture().setWorkPlace(msgFromQueue.getWorkPlace());
					newMsg.getWorkPlace().setFurniture(msgFromQueue.getFurniture());
				}

				boolean workerHasWorkplace = switch (type) {
					case CUTTING -> newMsg.getWorkerForCutting().getCurrentWorkPlace() != null;
					case STAINING -> newMsg.getWorkerForStaining().getCurrentWorkPlace() != null;
					case PAINTING -> newMsg.getWorkerForPainting().getCurrentWorkPlace() != null;
					case ASSEMBLY -> newMsg.getWorkerForAssembly().getCurrentWorkPlace() != null;
					case MONTAGE -> newMsg.getWorkerForMontage().getCurrentWorkPlace() != null;
				};

				if (workerHasWorkplace) {
					if (type == OperationType.CUTTING) {
						newMsg.setCode(Mc.rPresunDoSkladu);
						newMsg.setAddressee(mySim().findAgent(Id.agentPohybu));
						setWorkerState(newMsg, type, WorkerBussyState.MOVING_TO_STORAGE);
					} else {
						boolean sameWorkPlace = switch (type) {
							case STAINING ->
									newMsg.getWorkerForStaining().getCurrentWorkPlace() == newMsg.getWorkPlace();
							case PAINTING ->
									newMsg.getWorkerForPainting().getCurrentWorkPlace() == newMsg.getWorkPlace();
							case ASSEMBLY ->
									newMsg.getWorkerForAssembly().getCurrentWorkPlace() == newMsg.getWorkPlace();
							case MONTAGE -> newMsg.getWorkerForMontage().getCurrentWorkPlace() == newMsg.getWorkPlace();
							default -> false;
						};

						if (sameWorkPlace) {
							newMsg.setCode(switch (type) {
								case STAINING -> Mc.rUrobMorenie;
								case PAINTING -> Mc.rUrobLakovanie;
								case ASSEMBLY -> Mc.rUrobSkladanie;
								case MONTAGE -> Mc.rUrobMontaz;
								default -> -1;
							});
							newMsg.setAddressee(mySim().findAgent(Id.agentCinnosti));
							request(newMsg);
						} else {
							newMsg.setCode(Mc.rPresunZoSkladu);
							newMsg.setAddressee(mySim().findAgent(Id.agentPohybu));
							setWorkerState(newMsg, type, WorkerBussyState.MOVING_FROM_STORAGE);
						}
					}
				} else {
					if (type == OperationType.CUTTING) {
						newMsg.setCode(Mc.rPripravVSklade);
						newMsg.setAddressee(mySim().findAgent(Id.agentSkladu));
						setWorkerState(newMsg, type, WorkerBussyState.PREPARING_IN_STORAGE);
					} else {
						newMsg.setCode(Mc.rPresunNaPracovisko);
						newMsg.setAddressee(mySim().findAgent(Id.agentPohybu));
						setWorkerState(newMsg, type, WorkerBussyState.MOVE_TO_WORKPLACE);
					}
				}

				request(newMsg);
				continue;
			}

			if(type == OperationType.CUTTING) {
				if (!hasWorkPlace) {
					WorkPlace wp = tryAssignFreeWorkplace();
					if (wp != null) {
						msgFromQueue.setWorkPlace(wp);
						msgFromQueue.getFurniture().setWorkPlace(wp);
					}
				}
			}


			if (!hasWorker) {
				MyMessage reqWorker = new MyMessage(msgFromQueue);
				reqWorker.setCode(switch (type) {
					case CUTTING -> Mc.rVyberPracovnikaRezanie;
					case STAINING -> Mc.rVyberPracovnikaMorenie;
					case PAINTING -> Mc.rVyberPracovnikaLakovanie;
					case ASSEMBLY -> Mc.rVyberPracovnikaSkladanie;
					case MONTAGE -> Mc.rVyberPracovnikaMontaz;
				});
				reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
				request(reqWorker);
			}
			processed++;
		}
	}

	private int getAvailableResourcesCount(OperationType type) {
		MySimulation mySim = (MySimulation)mySim();
		int freeWorkers
		= switch (type) {
			case CUTTING -> mySim.getCountWorkerA();       // napr. WorkerA – rezanie
			case STAINING -> mySim.getCountWorkerC();      // morenie
			case PAINTING -> mySim.getCountWorkerC();      // lakovanie (tiež typ B?)
			case ASSEMBLY -> mySim.getCountWorkerB();      // skladanie
			case MONTAGE -> mySim.getCountWorkerC() + mySim.getCountWorkerA();
		};

		int freeWorkplaces = this.freeWorkplaces.size();

		return Math.min(freeWorkers, freeWorkplaces);
	}

	private void setWorkerState(MyMessage msg, OperationType type, WorkerBussyState state) {
		switch (type) {

			case CUTTING -> msg.getWorkerForCutting().setState(state.getValue(), mySim().currentTime());
			case STAINING -> msg.getWorkerForStaining().setState(state.getValue(), mySim().currentTime());
			case PAINTING -> msg.getWorkerForPainting().setState(state.getValue(), mySim().currentTime());
			case ASSEMBLY -> msg.getWorkerForAssembly().setState(state.getValue(), mySim().currentTime());
			case MONTAGE -> msg.getWorkerForMontage().setState(state.getValue(), mySim().currentTime());
		}
	}
	private boolean hasWorkerForType(MyMessage msg, OperationType type) {
		return switch (type) {
			case STAINING -> msg.getWorkerForStaining() != null;
			case PAINTING -> msg.getWorkerForPainting() != null;
			case ASSEMBLY -> msg.getWorkerForAssembly() != null;
			case MONTAGE -> msg.getWorkerForMontage() != null;
			default -> false;
		};
	}






	//pohyb
	//meta! sender="AgentPohybu", id="138", type="Response"
	public void processRPresunDoSkladu(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPripravVSklade);
		msg.setAddressee(mySim().findAgent(Id.agentSkladu));
		request(msg);
	}

	//meta! sender="AgentPohybu", id="157", type="Response"
	public void processRPresunNaPracovisko(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		if (msg.getWorkerForStaining() != null) {
			msg.setCode(Mc.rUrobMorenie);
		} else if (msg.getWorkerForPainting() != null) {
			msg.setCode(Mc.rUrobLakovanie);
		} else if (msg.getWorkerForAssembly() != null) {
			msg.setCode(Mc.rUrobSkladanie);
		} else if (msg.getWorkerForMontage() != null) {
			msg.setCode(Mc.rUrobMontaz);
		} else {
			throw new IllegalStateException("Presun na pracovisko: žiadny worker nie je nastavený.");
		}

		msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
		request(msg);
	}


	//meta! sender="AgentPohybu", id="385", type="Response"
	public void processRPresunZoSkladu(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		if (msg.getWorkerForCutting() != null) {
			msg.setCode(Mc.rUrobRezanie);
		} else if (msg.getWorkerForStaining() != null) {
			msg.setCode(Mc.rUrobMorenie);
		} else if (msg.getWorkerForPainting() != null) {
			msg.setCode(Mc.rUrobLakovanie);
		} else if (msg.getWorkerForAssembly() != null) {
			msg.setCode(Mc.rUrobSkladanie);
		} else if (msg.getWorkerForMontage() != null) {
			msg.setCode(Mc.rUrobMontaz);
		} else {
			throw new IllegalStateException("Presun zo skladu: žiadny worker nie je nastavený.");
		}

		msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
		request(msg);
	}



	// Pracovnici Response agent Pracovnikov

	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		queuePainting.getQueue().stream()
				.filter(q -> q.equals(msg))
				.findFirst()
				.ifPresent(found -> {
					found.setWorkerForPainting(msg.getWorkerForPainting());
					if (found.getWorkPlace() == null) {
						WorkPlace wp = tryAssignFreeWorkplace();
						if (wp != null) {
							found.setWorkPlace(wp);
							found.getFurniture().setWorkPlace(wp);
						}
					}
					if (found.getWorkerForPainting() != null && found.getWorkPlace() != null) {
						handleCompletePreparation(found, queuePainting, OperationType.PAINTING);
					}
				});
	}


	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		queueStaining.getQueue().stream()
				.filter(q -> q.equals(msg))
				.findFirst()
				.ifPresent(found -> {
					found.setWorkerForStaining(msg.getWorkerForStaining());
					if (found.getWorkPlace() == null) {
						WorkPlace wp = tryAssignFreeWorkplace();
						if (wp != null) {
							found.setWorkPlace(wp);
							found.getFurniture().setWorkPlace(wp);
						}
					}
					if (found.getWorkerForStaining() != null && found.getWorkPlace() != null) {
						handleCompletePreparation(found, queueStaining, OperationType.STAINING);
					}
				});
	}


	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		queueAssembly.getQueue().stream()
				.filter(q -> q.equals(msg))
				.findFirst()
				.ifPresent(found -> {
					found.setWorkerForAssembly(msg.getWorkerForAssembly());
					if (found.getWorkPlace() == null) {
						WorkPlace wp = tryAssignFreeWorkplace();
						if (wp != null) {
							found.setWorkPlace(wp);
							found.getFurniture().setWorkPlace(wp);
						}
					}
					if (found.getWorkerForAssembly() != null && found.getWorkPlace() != null) {
						handleCompletePreparation(found, queueAssembly, OperationType.ASSEMBLY);
					}
				});
	}



	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		queueMontage.getQueue().stream()
				.filter(q -> q.equals(msg))
				.findFirst()
				.ifPresent(found -> {
					found.setWorkerForMontage(msg.getWorkerForMontage());
					if (found.getWorkPlace() == null) {
						WorkPlace wp = tryAssignFreeWorkplace();
						if (wp != null) {
							found.setWorkPlace(wp);
							found.getFurniture().setWorkPlace(wp);
						}
					}
					if (found.getWorkerForMontage() != null && found.getWorkPlace() != null) {
						handleCompletePreparation(found, queueMontage, OperationType.MONTAGE);
					}
				});
	}



	//Cinnosti

	//meta! sender="AgentCinnosti", id="284", type="Response"
	public void processRUrobRezanie(MessageForm message) {

		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForCutting().setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
		MyMessage msgForReleaseWorker = new MyMessage(msg);
		//adding toQueueColoring
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.setWorkerForCutting(null);
		msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_STAINING.getValue());
		msg.getWorkPlace().setActivity(null);

		queueStaining.addLast(msg);

		msgForReleaseWorker.setCode(Mc.noticeUvolniRezanie);
		msgForReleaseWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		notice(msgForReleaseWorker);

		if (!queueMontage.isEmpty()) {
			checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		}
		if (!queueNonProcessed.isEmpty()) {
			checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);
		}
		if (!queueStaining.isEmpty()) {
			checkAllItemsQueue(queueStaining, OperationType.STAINING);
		}
		if (!queuePainting.isEmpty()) {
			checkAllItemsQueue(queuePainting, OperationType.PAINTING);
		}


	}

	//meta! sender="AgentCinnosti", id="286", type="Response"
	public void processRUrobMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) _mySim;
		msg.getWorkerForStaining().setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkPlace().setActivity(null);



		double rand = mySimulation.getGenerators().getRealisePaintingDist().nextDouble();

		if (rand < 0.15) {
			//System.out.println("rand = " + rand);
			msg.setWorkerForPainting(msg.getWorkerForStaining());
			msg.setWorkerForStaining(null);
			msg.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
			msg.setCode(Mc.rUrobLakovanie);
			msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
			request(msg);
		} else {
			MyMessage msgForReleaseWorker = new MyMessage(msg);
			msgForReleaseWorker.setWorkerForStaining(msg.getWorkerForStaining());
			msgForReleaseWorker.setCode(Mc.noticeUvolniMorenie);
			msgForReleaseWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
			notice(msgForReleaseWorker);

			msg.setWorkerForStaining(null);
			msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_ASSEMBLY.getValue());
			queueAssembly.addLast(msg);
		}

		if(!queueMontage.isEmpty()) {
			this.checkAllItemsQueue(this.queueMontage, OperationType.MONTAGE);
		}

		this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		this.checkAllItemsQueue(queueStaining, OperationType.STAINING);
		this.checkAllItemsQueue(queuePainting, OperationType.PAINTING);

	}


	//meta! sender="AgentCinnosti", id="289", type="Response"
	// Použitie v processRUrobMontaz
	public void processRUrobMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MyMessage msgForRelease = new MyMessage(msg);

		msg.getWorkerForMontage().setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkPlace().setActivity(null);

		msgForRelease.setCode(Mc.noticeUvolniMontaz);
		msgForRelease.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		notice(msgForRelease);

		msg.setWorkerForMontage(null);

		msg.getFurniture().setIsDone(mySim().currentTime());
		msg.getFurniture().getWorkPlace().setFurniture(null);
		releaseWorkPlace(msg.getFurniture().getWorkPlace());
		msg.getFurniture().setWorkPlace(null);

		if (msg.getFurniture().getOrder().isOrderFinished()) {
			msg.getFurniture().getOrder().setState(OrderStateValues.ORDER_DONE.getValue());
			MyMessage orderFinishedMSG = new MyMessage(msg);
			orderFinishedMSG.setOrder(orderFinishedMSG.getFurniture().getOrder());
			orderFinishedMSG.setCode(Mc.noticeHotovaObjednavka);
			orderFinishedMSG.setAddressee(mySim().findAgent(Id.agentModelu));
			notice(orderFinishedMSG);
		}

		this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		this.checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);
		this.checkAllItemsQueue(queueStaining, OperationType.STAINING);
		this.checkAllItemsQueue(queuePainting, OperationType.PAINTING);
	}



	//meta! sender="AgentCinnosti", id="288", type="Response"
	public void processRUrobSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		msg.getWorkerForAssembly().setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkPlace().setActivity(null);

		MyMessage msgForRelease = new MyMessage(msg);
		msgForRelease.setWorkerForAssembly(msg.getWorkerForAssembly());
		msgForRelease.setCode(Mc.noticeUvolniSkladanie);
		msgForRelease.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		notice(msgForRelease);

		msg.setWorkerForAssembly(null);

		if (msg.getFurniture().getType() == 3) {
			msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_MONTAGE.getValue());
			queueMontage.addLast(msg);

		} else {
			msg.getFurniture().setIsDone(mySim().currentTime());
			msg.getFurniture().getWorkPlace().setFurniture(null);
			releaseWorkPlace(msg.getFurniture().getWorkPlace());
			msg.getFurniture().setWorkPlace(null);

			if (msg.getFurniture().getOrder().isOrderFinished()) {
				msg.getFurniture().getOrder().setState(OrderStateValues.ORDER_DONE.getValue());
				MyMessage orderFinishedMSG = new MyMessage(msg);
				orderFinishedMSG.setOrder(orderFinishedMSG.getFurniture().getOrder());
				orderFinishedMSG.setCode(Mc.noticeHotovaObjednavka);
				orderFinishedMSG.setAddressee(mySim().findAgent(Id.agentModelu));
				notice(orderFinishedMSG);
			}
		}

		if (!queueMontage.isEmpty()) {
			this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		}
		this.checkAllItemsQueue(queueAssembly, OperationType.ASSEMBLY);
		this.checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);
		this.checkAllItemsQueue(queuePainting, OperationType.PAINTING);
		this.checkAllItemsQueue(queueStaining, OperationType.STAINING);
	}


	//meta! sender="AgentCinnosti", id="287", type="Response"
	public void processRUrobLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		MyMessage msgForRelease = new MyMessage(msg);

		msg.getWorkerForPainting().setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkPlace().setActivity(null);

		msgForRelease.setWorkerForPainting(msg.getWorkerForPainting());
		msgForRelease.setCode(Mc.noticeUvolniLakovanie);
		msgForRelease.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		notice(msgForRelease);

		msg.setWorkerForPainting(null);

		msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_ASSEMBLY.getValue());
		queueAssembly.addLast(msg);

		if (!queueMontage.isEmpty()) {
			this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
		}
		this.checkAllItemsQueue(queueAssembly, OperationType.ASSEMBLY);
		this.checkAllItemsQueue(queuePainting, OperationType.PAINTING);
		this.checkAllItemsQueue(queueStaining, OperationType.STAINING);

	}


	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.rPresunZoSkladu:
			processRPresunZoSkladu(message);
		break;

		case Mc.rPripravVSklade:
			processRPripravVSklade(message);
		break;

		case Mc.rUrobSkladanie:
			processRUrobSkladanie(message);
		break;

		case Mc.rUrobLakovanie:
			processRUrobLakovanie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.rVyberPracovnikaSkladanie:
			processRVyberPracovnikaSkladanie(message);
		break;

		case Mc.rUrobRezanie:
			processRUrobRezanie(message);
		break;

		case Mc.noticeSpracujObjednavku:
			processNoticeSpracujObjednavku(message);
		break;

		case Mc.rVyberPracovnikaMorenie:
			processRVyberPracovnikaMorenie(message);
		break;

		case Mc.rVyberPracovnikaRezanie:
			processRVyberPracovnikaRezanie(message);
		break;

		case Mc.rVyberPracovnikaLakovanie:
			processRVyberPracovnikaLakovanie(message);
		break;

		case Mc.rPresunNaPracovisko:
			processRPresunNaPracovisko(message);
		break;

		case Mc.rPresunDoSkladu:
			processRPresunDoSkladu(message);
		break;

		case Mc.rVyberPracovnikaMontaz:
			processRVyberPracovnikaMontaz(message);
		break;

		case Mc.rUrobMontaz:
			processRUrobMontaz(message);
		break;

		case Mc.rUrobMorenie:
			processRUrobMorenie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentNabytku myAgent()
	{
		return (AgentNabytku)super.myAgent();
	}

}
