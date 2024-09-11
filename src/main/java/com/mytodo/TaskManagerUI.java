// TaskManagerUI.java
package com.mytodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagerUI {
    private com.mytodo.TaskManager taskManager;
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;

    public TaskManagerUI() {
        taskManager = new com.mytodo.TaskManager();
        frame = new JFrame("To-Do List");
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
    }

    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JTextField taskField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Complete Task");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskDescription = taskField.getText();
                if (!taskDescription.isEmpty()) {
                    taskManager.addTask(taskDescription);
                    updateTaskList();
                    taskField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.removeTask(selectedIndex);
                    updateTaskList();
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.toggleTaskCompletion(selectedIndex);
                    updateTaskList();
                }
            }
        });

        panel.add(taskField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(completeButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void updateTaskList() {
        listModel.clear();
        for (Task task : taskManager.getTasks()) {
            listModel.addElement((task.isCompleted() ? "[✔] " : "[ ] ") + task.getDescription());
        }
    }
}