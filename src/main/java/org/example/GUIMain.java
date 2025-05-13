package org.example;

import GUI.AgentSimulationGUI;

public class GUIMain {
    public static void main(String[] args) {
        AgentSimulationGUI agentSimulationGUI = new AgentSimulationGUI();
        agentSimulationGUI.setVisible(true);
        System.out.println(GUIMain.class.getResource("../resources/images/worker_a.png"));
    }
}