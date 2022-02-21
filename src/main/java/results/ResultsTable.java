package results;

import player.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static player.Strategy.enabledStrategies;

public class ResultsTable {
  private final DefaultTableModel dm;
  private final String[] header;

  public ResultsTable() {
    JFrame frame = new JFrame();
    frame.setTitle("Prisoner's Dilemma");
    header = createHeaders();

    JTable table = new JTable();
    table.setBounds(30, 40, 1800, 1000);
    table.setRowHeight(40);
    table.setFont(new Font("Serif", Font.PLAIN, 40));
    dm = createDataModel(table);
    table.getColumnModel().getColumn(0).setPreferredWidth(200);

    generalLayoutSetup(frame, table);
  }

  private void generalLayoutSetup(JFrame frame, JTable table) {
    JScrollPane sp = new JScrollPane(table);
    frame.add(sp);
    frame.setSize(1800, 1000);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private DefaultTableModel createDataModel(JTable table) {
    final DefaultTableModel dm;
    dm = new DefaultTableModel(0, 0);
    dm.setColumnIdentifiers(header);
    table.setModel(dm);
    return dm;
  }

  private String[] createHeaders() {
    final String[] header;
    final Stream<String> strategies = enabledStrategies().stream()
        .map(Enum::name);
    final List<String> headerList = new ArrayList<>();
    headerList.add("Strategy");
    headerList.addAll(strategies.collect(Collectors.toList()));
    headerList.add("Average");
    header = headerList.toArray(new String[0]);
    return header;
  }

  public void addDataPoints(Player player) {
    final String strategyName = player.getStrategy().name();
    int index = getHeaderIndex(strategyName);
    final Vector<String> data = new Vector<>();
    data.add(player.getStrategy().name());
    data.addAll(player.getOpponentPointMap().values()
        .stream().map(Double::toString).collect(Collectors.toList()));
    data.add(index, "N/A");
    data.add(String.valueOf(player.getAverage()));
    dm.addRow(data);
  }

  private int getHeaderIndex(String strategyName) {
    for (int i = 0; i < header.length; i++) {
      if (header[i].equals(strategyName)) {
        return i;
      }
    }
    throw new RuntimeException("Could not find strategy.");
  }

}
