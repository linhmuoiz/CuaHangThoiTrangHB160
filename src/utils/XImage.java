/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
/**
 *
 * @author admim
 */
public class XImage {
        public static Image getAppIcon() {
            URL url = XImage.class.getResource("/icon/logo.png");
            return new ImageIcon(url).getImage();
    }
}
