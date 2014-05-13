import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Loader {
	public Loader() {

	}

	// the interface of the loder that gave u the choice to choose ur file and
	// check it extenison
	public Class<?> newCla() {
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String fileType = file.getName();
			try {
				if (fileType
						.substring(fileType.length() - 4, fileType.length())
						.equals(".jar")) {
					return (new JarLoader().getClass(file.getPath(),
							file.getName()));
				} else {
					if (fileType.substring(fileType.length() - 6,
							fileType.length()).equals(".class")) {
						return (new LoderCla().load(file.getPath()));
					} else {
						JOptionPane.showMessageDialog(null,
								"wrong file extenison");
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "wrong file extenison");
			}

		}
		return null;
	}
}

// jar loder is loding the class from ajar file
class JarLoader extends ClassLoader {
	private JarFile loadedFile;

	public JarLoader() {
	}

	public Class<?> getClass(String url, String cn) {

		try {
			loadedFile = new JarFile(url);

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		if (loadedFile != null) {
			return findClass(cn.substring(0, cn.length() - 4));
		} else {
			JOptionPane.showMessageDialog(null, "this file is not found: "
					+ "\n" + url);
		}
		return null;
	}

	// /method of get class from jar f
	protected Class<?> findClass(String name) {
		JarEntry entry = this.loadedFile.getJarEntry(name + ".class");
		if (entry == null) {
			JOptionPane.showMessageDialog(null, "class not found,\"" + name
					+ "\"");
			return null;
		}
		try {
			byte[] array = new byte[(int) entry.getSize()];
			InputStream in = this.loadedFile.getInputStream(entry);
			ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
			int length = in.read(array);
			while (length > 0) {
				out.write(array, 0, length);
				length = in.read(array);
			}
			return defineClass(name, out.toByteArray(), 0, out.size());
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "class not found,\"" + name
					+ "\"");
		}
		return null;

	}
}

// loading the dot class file to get class from it
class LoderCla extends ClassLoader {
	public LoderCla() {

	}

	// method that take url of the file to imort it
	public Class<?> load(String url) {
		try {
			FileInputStream in = new FileInputStream(url);
			byte array[] = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
			int length = in.read(array);
			while (length > 0) {
				out.write(array, 0, length);
				length = in.read(array);
			}
			return defineClass(out.toByteArray(), 0, out.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "wrong in file");
		}
		return null;
	}
}
