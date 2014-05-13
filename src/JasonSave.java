import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;

public class JasonSave {
	
	
	public String toJson(MyShape a){
		String string ;
		string = '\"' + a.getClass().getName() + '\"' + ':' + '{';
		string = string + '\"'+"topleftX"+'\"' + ':' + a.topLeftX + ',';
		string = string + '\"'+"topleftY"+'\"'+':' + a.topLeftY + ',';
		string = string + '\"'+"endX"+'\"' + ':'+ a.endX + ',';
		string = string + '\"'+"endY"+'\"'+':' + a.endY + ',';
		string = string + '\"'+"color"+ '\"' + ':' + '{';
		string = string + '\"' + "r" + '\"' + ':' + a.drawCol.getRed() + ',';
		string = string + '\"' + "g" + '\"' + ':' + a.drawCol.getGreen() + ',';
		string = string + '\"' + "b" + '\"' + ':' + a.drawCol.getBlue() + '}' + ',' ;
		string = string + '\"' + "fill" + '\"' + ':' + a.fill + '}';
		return string;
	}public void saveJson(String url){
		try {
			BufferedWriter save = new BufferedWriter(new FileWriter(url));
			save.write('{');
			save.write('\"' + "MyShape" + '\"' + ':' + '[' + '\n');
			MyShape s;
			if (!MyShape.his.isEmpty()) {
				Iterator<MyShape> t = MyShape.his.iterator();
				s = t.next();
				save.write(toJson(s) + '\n');
				System.out.println(toJson(s));
				while (t.hasNext()) {
					s = t.next();
					save.write(','+toJson(s)+'\n');
				}
				save.write( ']');
				save.write('}');
			}save.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
	}public MyShape loadJson(String s){
		MyShape sh = null;
		String temp ;
		int r = 0;
		int g = 0;
		int b = 0;
		
		
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='{'){
				temp = s.substring(0, i);
				s = s.substring(i);
				String temp1="";
				char c=',';
				for(int j=0;j<temp.length();j++ ){
					c=temp.charAt(j);
					if(c!=','&&c!='\"'&&c!=':'){
						temp1+=c;
					}
				}
				try {
					sh=(MyShape) Class.forName(temp1).getConstructor(new Class[]{Integer.TYPE,Integer.TYPE}).newInstance(0,0);
					System.out.println(MyShape.his.size()+"<<<<<<<<<<<<<<<");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("my flag of jason");
				}
				break;
			}
		}for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)==':') {
				s = s.substring(i + 1);
				for (int j = 0; j <s.length(); j++) {
					if(s.charAt(j)==','){
						temp = s.substring(0, j);
						s = s.substring(j + 1);
						sh.topLeftX = Integer.parseInt(temp);
						break;
					}
				}break;
			}
		}
	
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)==','){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					sh.topLeftY = Integer.parseInt(temp);
					break;
				}
			}break;
		}
	}
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)==','){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					sh.endX = Integer.parseInt(temp);
					break;
				}
			}break;
		}
	}

	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)==','){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					sh.endY = Integer.parseInt(temp);
					break;
				}
			}break;
		}
	}
	for (int i = 0; i < s.length(); i++) {
		if(s.charAt(i) == '{'){
			s = s.substring(i+1);
			break;
		}
	}
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)==','){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					r = Integer.parseInt(temp);
					break;
				}
			}break;
		}
	}for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)==','){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					g = Integer.parseInt(temp);
					break;
				}
			}break;
		}
	}
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)=='}'){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					b = Integer.parseInt(temp);
					sh.drawCol = new Color(r, g, b);
					break;
				}
			}break;
		}
	}
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)==':') {
			s = s.substring(i + 1);
			for (int j = 0; j <s.length(); j++) {
				if(s.charAt(j)=='}'){
					temp = s.substring(0, j);
					s = s.substring(j + 1);
					if(temp.equals("false"))sh.fill = false;
					else sh.fill = true;
					break;
				}
			}break;
		}
	}
	if(sh!=null){
	MyShape.mark.setMarkTop(sh.topLeftX, sh.topLeftY);
	sh.resizeBottom(sh.endX, sh.endY);
	}
	return sh;
	
	
	}
	public void load (String url){
		MyShape s = null;
		String temp = null;
		try {
			BufferedReader read = new BufferedReader(new FileReader(url));
			temp = read.readLine();
			System.out.println(temp);
			temp = read.readLine();
			while (temp!=null&&!temp.equals("]")) {
				s = loadJson(temp);
				MyShape.his.addLast(s);
				System.out.println(temp);
				temp = read.readLine();				
			}
			System.out.println("o");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	}
	
//	public static void main(String[] args) {
//		JasonSave s = new JasonSave();
////		MyShape a = s.loadJson('\"'+"MyRectangle"+'\"' +':'+ '{'+'\"'+"topleftX"+'\"'+':'+182+','+'\"'+"topleftY"+'\"'+':' +118 + ','+ '\"'+"endX"+'\"'+':'+1038+','+'\"'+"endY"+'\"'+':'+267+','+'\"'+"color"+'\"'+':'+'{'+'\"'+"r"+'\"'+':'+5+','+'\"'+"g"+'\"'+':'+5 +','+'\"'+ '\"'+"b"+'\"'+':'+5 +'}'+','+'\"'+"fill"+'\"'+':'+true+'}');
////		System.out.println('\"'+"MyRectangle"+'\"' +':'+ '{'+'\"'+"topleftX"+'\"'+':'+182+','+'\"'+"topleftY"+'\"'+':' +118 + ','+ '\"'+"endX"+'\"'+':'+1038+','+'\"'+"endY"+'\"'+':'+267+','+'\"'+"color"+'\"'+':'+'{'+'\"'+"r"+'\"'+':'+5+','+'\"'+"g"+'\"'+':'+5 +','+'\"'+ '\"'+"b"+'\"'+':'+5 +'}'+','+'\"'+"fill"+'\"'+':'+true+'}');
////	System.out.println(a.topLeftX);
////	System.out.println(a.topLeftY);
////	System.out.println(a.endX);
////	System.out.println(a.endY);
////	System.out.println(a.drawCol.getRed());
////	System.out.println(a.drawCol.getGreen());
////	System.out.println(a.drawCol.getBlue());
////	System.out.println(a.isFill);
//	s.load();
//	}
}
