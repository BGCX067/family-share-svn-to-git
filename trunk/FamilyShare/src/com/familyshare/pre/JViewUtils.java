package com.familyshare.pre;

import java.awt.Dimension;

public class JViewUtils {

	public static Dimension getScreeanSize() {
		java.awt.Toolkit kit = java.awt.Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		return screenSize;
	}

}
