package de.bischinger.parrot.gui;

/**
 * Created by Alexander Bischof on 10.10.15.
 */
@FunctionalInterface
public interface Command {
    byte[] getCommand(int counter, int... args);

    Command PCMD = (counter, speedTurn) -> new byte[]{2, 10, (byte) counter, 14, 0, 0, 0, 3, 0, 0, 0, 1,
            (byte) speedTurn[0], (byte) speedTurn[1]};

    Command JUMP = (counter, ifSetHigh) -> new byte[]{4, 11, (byte) counter, 15, 0, 0, 0, 3, 2,
            3, 0, (byte) (ifSetHigh[0] == 1 ? 1 : 0), 0, 0, 0};

    Command SimpleAnimation = (counter, enumOrdinal) -> new byte[]{4, 11, (byte) counter, 15, 0, 0, 0,
            3, 2, 4, 0, (byte) enumOrdinal[0], 0, 0, 0};

    Command Pong = (counter, enumOrdinal) -> new byte[]{2, 1, (byte) counter, 15, 0, 0, 0,
            0, 2, 4, 0, 0};

    Command AllStates = (counter, enumOrdinal) -> new byte[]{4, 11, (byte) counter, 15, 0, 0, 0,
            0, 2, 4, 0, 0};

    Command AudioTheme = (counter, themeNr) -> new byte[]{4, 11, (byte) counter, 15, 0, 0, 0,
            3, 12, 1, 0, (byte) themeNr[0], 0, 0, 0};

    Command Volume = (counter, soundVal) -> new byte[]{4, 11, (byte) counter, 12, 0, 0, 0,
            3, 12, 0, 0, (byte)soundVal[0], 0};

    Command AllSettings = (counter, enumOrdinal) -> new byte[]{4, 11, (byte) counter, 15, 0, 0, 0,
            0, 2, 0, 0, 0};
}
