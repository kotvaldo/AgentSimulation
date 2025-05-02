package agents.agentnabytku;

import Enums.*;
import OSPABA.*;
import entities.*;
import simulation.*;

import java.util.Iterator;

//meta! id="9"
public class ManagerNabytku extends Manager {
    private final QueueNonProcessed queueNonProcessed;
    private final QueueStaining queueStaining;
    private final QueueAssembly queueAssembly;
    private final QueueMontage queueMontage;
    private final QueuePainting queuePainting;

    public ManagerNabytku(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
        queueNonProcessed = new QueueNonProcessed(null);
        queueStaining = new QueueStaining(null);
        queueAssembly = new QueueAssembly(null);
        queueMontage = new QueueMontage(null);
        queuePainting = new QueuePainting(null);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
        queueNonProcessed.getQueue().clear();
        queueStaining.getQueue().clear();
        queueAssembly.getQueue().clear();
        queueMontage.getQueue().clear();
        queuePainting.getQueue().clear();
    }


    //meta! sender="AgentModelu", id="24", type="Notice"
    public void processInit(MessageForm message) {
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
            this.queueNonProcessed.getQueue().addLast(furnitureMsg);
            //System.out.println(queueNonProcessed.getQueue());
        }
        System.out.println(queueNonProcessed.getQueue().size());
        checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);

    }


    //meta! sender="AgentPracovisk", id="388", type="Response"
    public void processDajPracovneMiestoRezanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        queueNonProcessed.getQueue().stream().filter(q -> q.equals(msg)).findFirst().ifPresent(q -> {
            q.setWorkPlace(msg.getWorkPlace());
            q.getFurniture().setWorkPlace(msg.getFurniture().getWorkPlace());
        });
        if (msg.getWorkerForCutting() == null && msg.getWorkPlace() != null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.rVyberPracovnikaRezanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqPlace);
            return;
        }
        if (msg.getWorkerForCutting() != null && msg.getWorkPlace() != null) {
            handleCompleteCuttingPreparation(msg, queueNonProcessed);
        }
    }

    //meta! sender="AgentPracovnikov", id="168", type="Response"
    public void processRVyberPracovnikaRezanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        queueNonProcessed.getQueue().stream().filter(q -> q.equals(msg)).findFirst().ifPresent(q -> {
            q.setWorkerForCutting(msg.getWorkerForCutting());
        });
        if (msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.dajPracovneMiestoRezanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }
        if (msg.getWorkerForCutting() != null && msg.getWorkPlace() != null) {
            handleCompleteCuttingPreparation(msg, queueNonProcessed);
        }

    }

    private void handleCompleteCuttingPreparation(MyMessage msg, Queue queue) {
        msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
        msg.getFurniture().setWorkPlace(msg.getWorkPlace());
        msg.getWorkPlace().setFurniture(msg.getFurniture());

        queue.getQueue().removeIf(m -> m.equals(msg));

        if (msg.getWorkerForCutting().getCurrentWorkPlace() != null) {
            msg.setCode(Mc.rPresunDoSkladu);
            msg.setAddressee(mySim().findAgent(Id.agentPohybu));
            msg.getWorkerForCutting().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
        } else {
            msg.setCode(Mc.rPripravVSklade);
            msg.setAddressee(mySim().findAgent(Id.agentSkladu));
            msg.getWorkerForCutting().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
        }

        request(msg);
    }

    private void handleCompletePreparation(MyMessage msg, Queue queue, OperationType type) {

        queue.getQueue().removeIf(m -> m.equals(msg));

        WorkPlace current = switch (type) {
            case STAINING -> msg.getWorkerForStaining().getCurrentWorkPlace();
            case PAINTING -> msg.getWorkerForPainting().getCurrentWorkPlace();
            case ASSEMBLY -> msg.getWorkerForAssembly().getCurrentWorkPlace();
            case MONTAGE -> msg.getWorkerForMontage().getCurrentWorkPlace();
            default -> throw new IllegalArgumentException("Unsupported type in handleCompletePreparation");
        };

        if (current != null && current.equals(msg.getWorkPlace())) {
            msg.setCode(switch (type) {
                case STAINING -> Mc.rUrobMorenie;
                case PAINTING -> Mc.rUrobLakovanie;
                case ASSEMBLY -> Mc.rUrobSkladanie;
                case MONTAGE -> Mc.rUrobMontaz;
                default -> throw new IllegalArgumentException("Unsupported type in rUrob");
            });
            msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
        } else if (current == null) {
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
        msg.getWorkerForCutting().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue());
        msg.setAddressee(_mySim.findAgent(Id.agentPohybu));
        request(msg);
    }




    private void checkAllItemsQueue(Queue queue, OperationType type) {
        Iterator<MyMessage> iterator = queue.getQueue().iterator();
        while (iterator.hasNext()) {
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

            if (!hasWorkPlace) {
                MyMessage reqWorkPlace = new MyMessage(msgFromQueue);
                reqWorkPlace.setCode(switch (type) {
                    case CUTTING -> Mc.dajPracovneMiestoRezanie;
                    case STAINING -> Mc.rDajPracovneMiestoMorenie;
                    case PAINTING -> Mc.rDajPracovneMiestoLakovanie;
                    case ASSEMBLY -> Mc.rDajPracovneMiestoSkladanie;
                    case MONTAGE -> Mc.rDajPracovneMiestoMontaz;
                });
                reqWorkPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
                request(reqWorkPlace);
                continue;
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
        }
    }

    private void setWorkerState(MyMessage msg, OperationType type, WorkerBussyState state) {
        switch (type) {
            case CUTTING -> msg.getWorkerForCutting().setState(state.getValue());
            case STAINING -> msg.getWorkerForStaining().setState(state.getValue());
            case PAINTING -> msg.getWorkerForPainting().setState(state.getValue());
            case ASSEMBLY -> msg.getWorkerForAssembly().setState(state.getValue());
            case MONTAGE -> msg.getWorkerForMontage().setState(state.getValue());
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


    //meta! sender="AgentPracovisk", id="389", type="Response"
    public void processRDajPracovneMiestoLakovanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queuePainting.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> {
                    q.setWorkPlace(msg.getWorkPlace());
                    q.getFurniture().setWorkPlace(msg.getFurniture().getWorkPlace());
                });

        if (!hasWorkerForType(msg, OperationType.PAINTING)) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaLakovanie);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
            return;
        }

        handleCompletePreparation(msg, queuePainting, OperationType.PAINTING);
    }


    //meta! sender="AgentPracovisk", id="390", type="Response"
    public void processRDajPracovneMiestoMontaz(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueMontage.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> {
                    q.setWorkPlace(msg.getWorkPlace());
                    q.getFurniture().setWorkPlace(msg.getFurniture().getWorkPlace());
                });

        if (msg.getWorkerForMontage() == null && msg.getWorkPlace() != null) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaMontaz);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
            return;
        }

        if (msg.getWorkerForMontage() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueMontage, OperationType.MONTAGE);
        }
    }


    //meta! sender="AgentPracovisk", id="182", type="Response"
    public void processRDajPracovneMiestoMorenie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueStaining.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> {
                    q.setWorkPlace(msg.getWorkPlace());
                    q.getFurniture().setWorkPlace(msg.getFurniture().getWorkPlace());
                });

        if (msg.getWorkerForStaining() == null && msg.getWorkPlace() != null) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaMorenie);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
            return;
        }

        if (msg.getWorkerForStaining() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueStaining, OperationType.STAINING);
        }
    }


    //meta! sender="AgentPracovisk", id="387", type="Response"
    public void processRDajPracovneMiestoSkladanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueAssembly.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> {
                    q.setWorkPlace(msg.getWorkPlace());
                    q.getFurniture().setWorkPlace(msg.getFurniture().getWorkPlace());
                });

        if (msg.getWorkerForAssembly() == null && msg.getWorkPlace() != null) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaSkladanie);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
            return;
        }

        if (msg.getWorkerForAssembly() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueAssembly, OperationType.ASSEMBLY);
        }
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
                .ifPresent(q -> q.setWorkerForPainting(msg.getWorkerForPainting()));

        if (msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.rDajPracovneMiestoLakovanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }

        if (msg.getWorkerForPainting() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queuePainting, OperationType.PAINTING);
        }
    }


    //meta! sender="AgentPracovnikov", id="XXX", type="Response"
    public void processRVyberPracovnikaMorenie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueStaining.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> q.setWorkerForStaining(msg.getWorkerForStaining()));

        if (msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.rDajPracovneMiestoMorenie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }

        if (msg.getWorkerForStaining() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueStaining, OperationType.STAINING);
        }
    }


    //meta! sender="AgentPracovnikov", id="XXX", type="Response"
    public void processRVyberPracovnikaSkladanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueAssembly.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> q.setWorkerForAssembly(msg.getWorkerForAssembly()));

        if (msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.rDajPracovneMiestoSkladanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }

        if (msg.getWorkerForAssembly() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueAssembly, OperationType.ASSEMBLY);
        }
    }


    //meta! sender="AgentPracovnikov", id="XXX", type="Response"
    public void processRVyberPracovnikaMontaz(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        queueMontage.getQueue().stream()
                .filter(q -> q.equals(msg))
                .findFirst()
                .ifPresent(q -> q.setWorkerForMontage(msg.getWorkerForMontage()));

        if (msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.rDajPracovneMiestoMontaz);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }

        if (msg.getWorkerForMontage() != null && msg.getWorkPlace() != null) {
            handleCompletePreparation(msg, queueMontage, OperationType.MONTAGE);
        }
    }



    //Cinnosti

    //meta! sender="AgentCinnosti", id="284", type="Response"
    public void processRUrobRezanie(MessageForm message) {

        MyMessage msg = (MyMessage) message.createCopy();
        msg.getWorkerForCutting().setState(WorkerBussyState.NON_BUSY.getValue());
        MyMessage msgForReleaseWorker = new MyMessage(msg);
        //adding toQueueColoring
        msg.getWorkPlace().setActualWorkingWorker(null);
        msg.setWorkerForCutting(null);
        msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_STAINING.getValue());
        msg.getWorkPlace().setActivity(null);

        queueStaining.getQueue().addLast(msg);

        msgForReleaseWorker.setCode(Mc.noticeUvolniRezanie);
        msgForReleaseWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
        notice(msgForReleaseWorker);

        if (!queueMontage.getQueue().isEmpty()) {
            checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
        }
        if (!queueNonProcessed.getQueue().isEmpty()) {
            checkAllItemsQueue(queueNonProcessed, OperationType.CUTTING);
        }
        if (!queueStaining.getQueue().isEmpty()) {
            checkAllItemsQueue(queueStaining, OperationType.STAINING);
        }
        if (!queuePainting.getQueue().isEmpty()) {
            checkAllItemsQueue(queuePainting, OperationType.PAINTING);
        }


    }

    //meta! sender="AgentCinnosti", id="286", type="Response"
    public void processRUrobMorenie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        MySimulation mySimulation = (MySimulation) _mySim;
        MyMessage msgForReleaseWorker = new MyMessage(msg);

        msg.getWorkerForStaining().setState(WorkerBussyState.NON_BUSY.getValue());
        msg.getWorkPlace().setActualWorkingWorker(null);
        msg.getWorkPlace().setActivity(null);

        msgForReleaseWorker.setWorkerForStaining(msg.getWorkerForStaining());
        msgForReleaseWorker.setCode(Mc.noticeUvolniC);
        msgForReleaseWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
        notice(msgForReleaseWorker);

        msg.setWorkerForStaining(null);


        double rand = mySimulation.getGenerators().getRealisePaintingDist().nextDouble();

        if (rand < 0.15) {
            msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_PAINTING.getValue());
            queuePainting.getQueue().addLast(msg);
        } else {
            msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_ASSEMBLY.getValue());
            queueAssembly.getQueue().addLast(msg);
        }

        if(!queueMontage.getQueue().isEmpty()) {
            this.checkAllItemsQueue(this.queueMontage, OperationType.MONTAGE);
        }

        this.checkAllItemsQueue(this.queuePainting, OperationType.PAINTING);

        this.checkAllItemsQueue(this.queueAssembly, OperationType.ASSEMBLY);


    }


    //meta! sender="AgentCinnosti", id="289", type="Response"
    public void processRUrobMontaz(MessageForm message) {

    }


    //meta! sender="AgentCinnosti", id="288", type="Response"
    public void processRUrobSkladanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        msg.getWorkerForAssembly().setState(WorkerBussyState.NON_BUSY.getValue());
        msg.getWorkPlace().setActualWorkingWorker(null);
        msg.getWorkPlace().setActivity(null);

        MyMessage msgForRelease = new MyMessage(msg);
        msgForRelease.setWorkerForAssembly(msg.getWorkerForAssembly());
        msgForRelease.setCode(Mc.noticeUvolniB);
        msgForRelease.setAddressee(mySim().findAgent(Id.agentPracovnikov));
        notice(msgForRelease);

        msg.setWorkerForAssembly(null);

        if (msg.getFurniture().getType() == 3) {
            msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_MONTAGE.getValue());
            queueMontage.getQueue().addLast(msg);

        } else {
            MyMessage msgForReleaseWorkPlace = new MyMessage(msg);
            msg.getFurniture().setIsDone();
            msg.getFurniture().getWorkPlace().setFurniture(null);
            msg.getFurniture().getWorkPlace().setState(WorkPlaceStateValues.NOT_WORKING.getValue());
            msg.getFurniture().setWorkPlace(null);

            msgForReleaseWorkPlace.setCode(Mc.noticeUvolniPracovneMiesto);
            msgForReleaseWorkPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            notice(msgForReleaseWorkPlace);

            if (msg.getFurniture().getOrder().isOrderFinished()) {
                msg.getFurniture().getOrder().setState(OrderStateValues.ORDER_DONE.getValue());
                MyMessage orderFinishedMSG = new MyMessage(msg);
                orderFinishedMSG.setOrder(orderFinishedMSG.getFurniture().getOrder());
                orderFinishedMSG.setCode(Mc.noticeHotovaObjednavka);
                orderFinishedMSG.setAddressee(mySim().findAgent(Id.agentModelu));
                notice(orderFinishedMSG);
            }
        }

        if (!queueMontage.getQueue().isEmpty()) {
            this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
        }
        this.checkAllItemsQueue(queueAssembly, OperationType.ASSEMBLY);

    }


    //meta! sender="AgentCinnosti", id="287", type="Response"
    public void processRUrobLakovanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();

        MyMessage msgForRelease = new MyMessage(msg);

        msg.getWorkerForPainting().setState(WorkerBussyState.NON_BUSY.getValue());
        msg.getWorkPlace().setActualWorkingWorker(null);
        msg.getWorkPlace().setActivity(null);

        msgForRelease.setWorkerForPainting(msg.getWorkerForPainting());
        msgForRelease.setCode(Mc.noticeUvolniC);
        msgForRelease.setAddressee(mySim().findAgent(Id.agentPracovnikov));
        notice(msgForRelease);

        msg.setWorkerForPainting(null);

        msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_ASSEMBLY.getValue());
        queueAssembly.getQueue().addLast(msg);

        if (!queueMontage.getQueue().isEmpty()) {
            this.checkAllItemsQueue(queueMontage, OperationType.MONTAGE);
        }
        this.checkAllItemsQueue(queueAssembly, OperationType.ASSEMBLY);
    }



    // Prichod novej objednavky


    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.rDajPracovneMiestoMontaz:
                processRDajPracovneMiestoMontaz(message);
                break;

            case Mc.rUrobLakovanie:
                processRUrobLakovanie(message);
                break;

            case Mc.dajPracovneMiestoRezanie:
                processDajPracovneMiestoRezanie(message);
                break;

            case Mc.rPresunZoSkladu:
                processRPresunZoSkladu(message);
                break;

            case Mc.rVyberPracovnikaLakovanie:
                processRVyberPracovnikaLakovanie(message);
                break;

            case Mc.init:
                processInit(message);
                break;

            case Mc.rUrobRezanie:
                processRUrobRezanie(message);
                break;

            case Mc.rUrobMorenie:
                processRUrobMorenie(message);
                break;

            case Mc.rVyberPracovnikaSkladanie:
                processRVyberPracovnikaSkladanie(message);
                break;

            case Mc.noticeSpracujObjednavku:
                processNoticeSpracujObjednavku(message);
                break;

            case Mc.rUrobSkladanie:
                processRUrobSkladanie(message);
                break;

            case Mc.rVyberPracovnikaRezanie:
                processRVyberPracovnikaRezanie(message);
                break;

            case Mc.rPripravVSklade:
                processRPripravVSklade(message);
                break;

            case Mc.rPresunNaPracovisko:
                processRPresunNaPracovisko(message);
                break;

            case Mc.rUrobMontaz:
                processRUrobMontaz(message);
                break;

            case Mc.rPresunDoSkladu:
                processRPresunDoSkladu(message);
                break;

            case Mc.rDajPracovneMiestoLakovanie:
                processRDajPracovneMiestoLakovanie(message);
                break;

            case Mc.rDajPracovneMiestoMorenie:
                processRDajPracovneMiestoMorenie(message);
                break;

            case Mc.rDajPracovneMiestoSkladanie:
                processRDajPracovneMiestoSkladanie(message);
                break;

            case Mc.rVyberPracovnikaMontaz:
                processRVyberPracovnikaMontaz(message);
                break;

            case Mc.rVyberPracovnikaMorenie:
                processRVyberPracovnikaMorenie(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentNabytku myAgent() {
        return (AgentNabytku) super.myAgent();
    }

}
