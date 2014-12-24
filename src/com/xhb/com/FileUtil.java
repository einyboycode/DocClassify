package com.xhb.com;

import java.util.*;
import java.io.*;

public class FileUtil {

	public static void writeLine(File file, String line) {

		BufferedWriter writer = null;

		try {

			writer = new BufferedWriter(new FileWriter(file));
			writer.write(line);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeHashMap(File file, Map<?, ?> map) {

		BufferedWriter writer = null;

		try {

			writer = new BufferedWriter(new FileWriter(file));
			Set<?> s = map.entrySet();
			Iterator<?> it = s.iterator();
			while (it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				writer.write(m.getKey() + "\t" + m.getValue() + "\n");
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}