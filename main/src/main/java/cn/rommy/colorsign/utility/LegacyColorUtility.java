package cn.rommy.colorsign.utility;

import org.bukkit.ChatColor;

public final class LegacyColorUtility {
    public static String replaceAll(char colorChar, String string) {
        if(string == null) return "";
        return ChatColor.translateAlternateColorCodes(colorChar, string);
    }
    
    public static String replaceColor(char colorChar, String string) {
        String specific = "0123456789AaBbCcDdEeFf";
        return replaceSpecific(colorChar, specific, string);
    }
    
    public static String replaceFormat(char colorChar, String string) {
        String specific = "KkLlMmNnOoRr";
        return replaceSpecific(colorChar, specific, string);
    }
    
    public static String replaceSpecific(char colorChar, String specific, String string) {
        char[] charArray = string.toCharArray();
        for(int i = 0; i < (charArray.length- 1); i++) {
            boolean hasColor1 = (colorChar == charArray[i]);
            boolean hasColor2 = (specific.indexOf(charArray[i + 1]) > -1);
            if(hasColor1 && hasColor2) {
                charArray[i] = ChatColor.COLOR_CHAR;
                charArray[i + 1] = Character.toLowerCase(charArray[i + 1]);
            }
        }
        
        return new String(charArray);
    }
}