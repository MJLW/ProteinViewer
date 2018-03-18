package util;

import math.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PDBReader {

    public String readFromPDBFile(String filePath) {
        BufferedReader br = null;
        String lines = "";
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            lines = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Crashed while reading PDB file: " + filePath);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Failed to close reader. File: " + filePath);
            }
        }

//        System.out.println(lines);
        return lines;
    }

    public ArrayList<Vector3f>[] parseAtoms(String pdbLines) {
        ArrayList<Vector3f> atomPositions = new ArrayList<>();
        ArrayList<Vector3f> colors = new ArrayList<>();
        for (String line : pdbLines.split("\n")) {
            if (line.length() > 5) {
                String lineType = line.substring(0, 6);
                if (lineType.contains("ATOM")) {
//                System.out.println(line);
                    Vector3f position = new Vector3f(
                            Float.parseFloat(line.substring(30, 37)),
                            Float.parseFloat(line.substring(38, 45)),
                            Float.parseFloat(line.substring(46, 53))
                    );
                    atomPositions.add(position);

                    String atomType = line.substring(77, 79).toLowerCase();
                    Vector3f color = null;
                    if(atomType.contains("h")) {
                        color = new Vector3f(0, 0, 1);
                    } else if(atomType.contains("c")) {
                        color = new Vector3f(1, 0, 0);
                    } else if(atomType.contains("o")) {
                        color = new Vector3f(0, 1, 0);
                    } else if(atomType.contains("n")) {
                        color = new Vector3f(1, 1, 0);
                    } else if(atomType.contains("s")) {
                        color = new Vector3f(1, 0, 1);
                    } else {
                        System.out.println(atomType);
                        color = new Vector3f(1, 1, 1);
                    }

                    colors.add(color);

                }
            }
        }
        return new ArrayList[] {atomPositions, colors};
    }

}
