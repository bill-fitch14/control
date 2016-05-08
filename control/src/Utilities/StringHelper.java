package Utilities;

import java.util.*;
import java.lang.*;
import java.lang.reflect.*;

public class StringHelper {

	public String NewLine = "\r\n";

	/// <summary>
	/// Don't allow instantiating this class.
	/// </summary>
	private StringHelper()
	{
	} // StringHelper()


	//Comparing, Counting and Extraction
	/// <summary>
	/// Check if a String (s1, can be longer as s2) begins with another
	/// String (s2), only if s1 begins with the same String data as s2,
	/// true is returned, else false. The String compare is case insensitive.
	/// </summary>
	public boolean BeginsWith(String s1, String s2)
	{
		if (s1 == null || s1.isEmpty() ||
			s2 == null || s2.isEmpty())
			return false;

		return s1.startsWith(s2);
	} // BeginsWith(s1, s2)

	/// <summary>
	/// Helps to compare Strings, uses case insensitive comparison.
	/// String.Compare is also gay because we have always to check for == 0.
	/// </summary>
	public boolean Compare(String s1, String s2)
	{
		if (s1 == null || s1.isEmpty() ||
			s2 == null || s2.isEmpty())
			return false;

		return s1.equalsIgnoreCase(s2);
	} // Compare(s1, s2)

	/// <summary>
	/// Helps to compare Strings, uses case insensitive comparison.
	/// String.Compare is also gay because we have always to check for == 0.
	/// This overload allows multiple Strings to be checked, if any of
	/// them matches we are good to go (e.g. ("hi", {"hey", "hello", "hi"})
	/// will return true).
	/// </summary>
	public boolean Compare(String s1, String[] anyMatch) throws Exception
	{
		if (anyMatch == null)
			throw new Exception("Unable to execute method without valid anyMatch array.");

		for (String match : anyMatch)
			if (s1.equalsIgnoreCase(match))
				return true;
		return false;
	} // Compare(s1, anyMatch)

	/// <summary>
	/// Is a specific name in a list of Strings?
	/// </summary>
	static public boolean IsInList(
		String name,
		ArrayList<String> list,
		boolean ignoreCase) throws Exception
	{
		if (list == null)
			throw new Exception("Unable to execute method without valid list.");

		for(String listEntry : list)
			if (name.equalsIgnoreCase(listEntry))
				return true;
		return false;
	} // IsInList(name, list, ignoreCase)

	/// <summary>
	/// Is a specific name in a list of Strings?
	/// </summary>
	static public boolean IsInList(
		String name,
		String[] list,
		boolean ignoreCase) throws Exception
	{
		if (list == null)
			throw new Exception(
				"Unable to execute method without valid list.");

		for (String listEntry : list)
			if (name.equalsIgnoreCase(listEntry))
				return true;
		return false;
	} // IsInList(name, list, ignoreCase)

	/// <summary>
	/// Count words in a text (words are only separated by ' ' (spaces))
	/// </summary>
	static public int CountWords(String text) throws Exception
	{
		if (text == null)
			throw new Exception(
				"Unable to execute method without valid text.");
		return text.split(" ").length;
	} // CountWords(text)
	/// <summary>
	/// Compare char case insensitive
	/// </summary>
	/// <param name="c1">C 1</param>
	/// <param name="c2">C 2</param>
	/// <returns>Boolean</returns>
	public static boolean CompareCharCaseInsensitive(char c1, char c2)
	{
		return Character.toLowerCase(c1) == Character.toLowerCase(c2);
		// Another way (slower):
		// return String.Compare("" + c1, "" + c2, true) == 0;
	} // CompareCharCaseInsensitive(c1, c2)

	/// <summary>
	/// Get last word
	/// </summary>
	/// <param name="text">Text</param>
	/// <returns>String</returns>
	public static String GetLastWord(String text) throws Exception
	{
		if (text == null)
			throw new Exception("Unable to execute method without valid text.");

		String[] words = text.split(" ");
		if (words.length > 0)
			return words[words.length - 1];
		return text;
	} // GetLastWord(text)

	/// <summary>
	/// Remove last word
	/// </summary>
	/// <param name="text">Text</param>
	/// <returns>String</returns>
	public static String RemoveLastWord(String text) throws Exception
	{
		if (text == null || text.isEmpty())
			return "";

		String lastWord = GetLastWord(text);
		// Fix 2004-10-08: new length can be 0 for killing first word
		if (text.equals(lastWord))
			return "";
		else if (lastWord.length() == 0 || text.length() == 0 ||
			text.length() - lastWord.length() - 1 <= 0)
			return text;
		else
			return text.substring(0, text.length() - lastWord.length() - 1);
	} // RemoveLastWord(text)
	/// <summary>
	/// Get last word
	/// </summary>
	/// <param name="text">Text</param>
	/// <returns>String</returns>
	public static String GetFirstWord(String text) throws Exception
	{
		if (text == null)
			throw new Exception("Unable to execute method without valid text.");

		String[] words = text.split("_");
		if (words.length > 0)
			return words[0];
		return text;
	} // GetLastWord(text)
	/// <summary>
	/// Remove last word
	/// </summary>
	/// <param name="text">Text</param>
	/// <returns>String</returns>
	public static String RemoveFirstWord(String text) throws Exception
	{
		if (text == null || text.isEmpty())
			return "";

		String lastWord = GetFirstWord(text);
		// Fix 2004-10-08: new length can be 0 for killing first word
		if (text.equals(lastWord))
			return "";
		else if (lastWord.length() == 0 || text.length() == 0 ||
			text.length() - lastWord.length() - 1 <= 0)
			return text;
		else
			return text.substring(0, text.length());
	} // RemoveLastWord(text)
//
//	/// <summary>
//	/// Get all spaces and tabs at beginning
//	/// </summary>
//	/// <param name="text">Text</param>
//	/// <returns>String</returns>
//	static public String GetAllSpacesAndTabsAtBeginning(String text)
//	{
//		if (text == null)
//			throw new Exception("Unable to execute method without valid text.");
//
//		StringBuilder ret = new StringBuilder();
//		for (int pos = 0; pos < text.length(); pos++)
//		{
//			if (text.charAt(pos) == ' ' ||
//				text.charAt(pos) == '\t')
//				ret.append(text.charAt(pos));
//			else
//				break;
//		} // for (pos)
//		return ret.toString();
//	} // GetAllSpacesAndTabsAtBeginning(text)
//
//	/// <summary>
//	/// Get tab depth
//	/// </summary>
//	/// <param name="text">Text</param>
//	/// <returns>Int</returns>
//	static public int GetTabDepth(String text)
//	{
//		if (text == null)
//			throw new Exception("Unable to GetTabDepth without valid text.");
//
//		for (int textPos = 0; textPos < text.length(); textPos++)
//			if (text.charAt(textPos) != '\t')
//				return textPos;
//		return text.length();
//	} // GetTabDepth(text)
//
//	/// <summary>
//	/// Check String word length
//	/// </summary>
//	public static String CheckStringWordLength(
//		String originalText, int maxLength)
//	{
//		if (originalText == null)
//			throw new Exception("Unable to execute method without valid text.");
//
//		String[] splitted = originalText.split(" ");
//		StringBuilder ret = new StringBuilder();
//		for (String word : splitted)
//		{
//			if (word.length() <= maxLength)
//				ret.append(word + " ");
//			else
//			{
//				for (int i = 0; i < word.length() / maxLength; i++)
//					ret.append(word.substring(i * maxLength, maxLength) + " ");
//			} // else
//		} // for (word : splitted)
//		return ret.toString().trim();
//	} // CheckStringWordLength(originalText, maxLength)
//
//	/// <summary>
//	/// Is searchName contained in textToCheck, will check case insensitive,
//	/// for a normal case sensitive test use textToCheck.Contains(searchName)
//	/// </summary>
//	/// <param name="textToCheck">Text to check</param>
//	/// <param name="searchName">Search name</param>
//	/// <returns>Bool</returns>
//	public static boolean Contains(String textToCheck, String searchName)
//	{
//		if (textToCheck == null || textToCheck.isEmpty() ||
//			searchName == null || searchName.isEmpty())
//			return false;
//
//		return textToCheck.toLowerCase().contains(searchName.toLowerCase());
//	} // Contains(textToCheck, searchName)
//
//	/// <summary>
//	/// Is any of the names in searchNames contained in textToCheck,
//	/// will check case insensitive, for a normal case sensitive test
//	/// use textToCheck.Contains(searchName).
//	/// </summary>
//	/// <param name="textToCheck">String to check</param>
//	/// <param name="searchNames">Search names</param>
//	/// <returns>Bool</returns>
//	public static boolean contains(String textToCheck, String[] searchNames)
//	{
//		if (textToCheck == null || textToCheck.isEmpty() ||
//			searchNames == null)
//			return false;
//
//		String StringToCheckLower = textToCheck.toLowerCase();
//		for (String name : searchNames)
//			if (StringToCheckLower.contains(name.toLowerCase()))
//				return true;
//		// Nothing found, no searchNames is contained in textToCheck
//		return false;
//	} // Contains(textToCheck, searchNames)
//
//	/// <summary>
//	/// Returns a String with the array data, byte array version.
//	/// </summary>
//	static public String WriteArrayData(byte[] byteArray)
//	{
//		StringBuilder ret = new StringBuilder();
//		if (byteArray != null)
//			for (int i = 0; i < byteArray.length; i++)
//				ret.append((ret.length() == 0 ? "" : ", ") +
//					Byte.toString(byteArray[i]));
//		return ret.toString();
//	} // WriteArrayData(byteArray)
//
//	/// <summary>
//	/// Returns a String with the array data, int array version.
//	/// </summary>
//	static public String WriteArrayData(int[] intArray)
//	{
//		StringBuilder ret = new StringBuilder();
//		if (intArray != null)
//			for (int i = 0; i < intArray.length; i++)
//				ret.append((ret.length() == 0 ? "" : ", ") +
//					Integer.toString(intArray[i]));
//		return ret.toString();
//	} // WriteArrayData(intArray)
//
//	/// <summary>
//	/// Returns a String with the array data, general array version.
//	/// </summary>
//	static public String WriteArrayData(Array array)
//	{
//		StringBuilder ret = new StringBuilder();
//		if (array != null)
//			for (int i = 0; i < array.getLength(array); i++)
//				ret.append((ret.length() == 0 ? "" : ", ") +
//					(array.get(array,i) == null ?
//					"null" : array.get(array,i).toString()));
//		return ret.toString();
//	} // WriteArrayData(array)
//
//	/// <summary>
//	/// Returns a String with the array data, general array version
//	/// with maxLength bounding (will return String with max. this
//	/// number of entries).
//	/// </summary>
//	static public String WriteArrayData(Array array, int maxLength)
//	{
//		StringBuilder ret = new StringBuilder();
//		if (array != null)
//			for (int i = 0; i < array.getLength(array) && i < maxLength; i++)
//				ret.append((ret.length() == 0 ? "" : ", ") +
//					array.get(array,i).toString());
//		return ret.toString();
//	} // WriteArrayData(array, maxLength)
//
//	/// <summary>
//	/// Returns a String with the array data, ArrayList version.
//	/// </summary>
//	static public String WriteArrayData(ArrayList array)
//	{
//		StringBuilder ret = new StringBuilder();
//		if (array != null)
//			for (Object obj : array)
//				ret.append((ret.length() == 0 ? "" : ", ") + obj.toString());
//		return ret.toString();
//	} // WriteArrayData(array)

//	/// <summary>
//	/// Write into space String, useful for writing parameters without
//	/// knowing the length of each String, e.g. when writing numbers
//	/// (-1, 1.45, etc.). You can use this function to give all Strings
//	/// the same width in a table. Maybe there is already a String function
//	/// for this, but I don't found any useful stuff.
//	/// </summary>
//	static public String WriteIntoSpaceString(String message, int spaces)
//	{
//		if (message == null)
//			throw new Exception("Unable to execute method without valid text.");
//
//		// Msg is already that long or longer?
//		if (message.length() >= spaces)
//			return message;
//
//		// Create String with number of specified spaces
//		char[] ret = new char[spaces];
//
//		// Copy data
//		int i;
//		for (i = 0; i < message.length(); i++)
//			ret[i] = message.charAt(i);
//		// Fill rest with spaces
//		for (i = message.length(); i < spaces; i++)
//			ret[i] = ' ';
//
//		// Return result
//		return new String(ret);
//	} // WriteIntoSpaceString(message, spaces)
//
//	/// <summary>
//	/// Write Iso Date (Year-Month-Day)
//	/// </summary>
///*	public static String WriteIsoDate(Date date)
//	{
//		return date.getYear() + "-" +
//			date.getMonth().toString() + "-" +
//			date.getDay().ToString("00");
//	} // WriteIsoDate(date)
//
//	/// <summary>
//	/// Write Iso Date and time (Year-Month-Day Hour:Minute)
//	/// </summary>
//	public static String WriteIsoDateAndTime(DateTime date)
//	{
//		return date.Year + "-" +
//			date.Month.ToString("00") + "-" +
//			date.Day.ToString("00") + " " +
//			date.Hour.ToString("00") + ":" +
//			date.Minute.ToString("00");
//	} // WriteIsoDateAndTime(date)
//
//	/// <summary>
//	/// Write internet time
//	/// </summary>
//	/// <param name="time">Time</param>
//	/// <param name="daylightSaving">Daylight saving</param>
//	/// <returns>String</returns>
//	public static String WriteInternetTime(
//		DateTime time,
//		bool daylightSaving)
//	{
//		return "@" + ((float)((int)(time.ToUniversalTime().AddHours(
//			daylightSaving ? 1 : 0).TimeOfDay.
//			TotalSeconds * 100000 / (24 * 60 * 60))) / 100.0f).ToString(
//			NumberFormatInfo.InvariantInfo);
//	} // WriteInternetTime(time, daylightSaving)*/
//
//	/// <summary>
//	/// Convert String data to int array, String must be in the form
//	/// "1, 3, 8, 7", etc. WriteArrayData is the complementar function.
//	/// </summary>
//	/// <returns>int array, will be null if String is invalid!</returns>
//	static public int[] ConvertStringToIntArray(String s)
//	{
//		// Invalid?
//		if (s == null || s.isEmpty())
//			return null;
//
//		String[] splitted = s.split(" ");
//			int[] ret = new int[splitted.length];
//			for (int i = 0; i < ret.length; i++)
//			{
//				Integer.parseInt(splitted[i]);
//			} // for (i)
//		return ret;
//	} // ConvertStringToIntArray(str)
//
//	/// <summary>
//	/// Convert String data to float array, String must be in the form
//	/// "1.5, 3.534, 8.76, 7.49", etc. WriteArrayData is the complementar
//	/// function.
//	/// </summary>
//	/// <returns>float array, will be null if String is invalid!</returns>
//	static public float[] ConvertStringToFloatArray(String s)
//	{
//		// Invalid?
//		if ((s == null || s.isEmpty()))
//			return null;
//
//		String[] splitted = s.split(" ");
//			float[] ret = new float[splitted.length];
//			for (int i = 0; i < ret.length; i++)
//			{
//				Float.parseFloat(splitted[i]);
//			} // for (i)
//		return ret;
//	} // ConvertStringToIntArray(str)
//	
//	/// <summary>
//	/// Extracts filename from full path+filename, cuts of extension
//	/// if cutExtension is true. Can be also used to cut of directories
//	/// from a path (only last one will remain).
//	/// </summary>
//	static public String ExtractFilename(String pathFile, boolean cutExtension)
//	{
//		if (pathFile == null || pathFile.isEmpty())
//			return "";
//
//		String[] fileName = pathFile.split(" ");
//		if (fileName.length == 0)
//		{
//			if (cutExtension)
//				return CutExtension(pathFile);
//			return pathFile;
//		} // if (fileName.length)
//
//		if (cutExtension)
//			return CutExtension(fileName[fileName.length - 1]);
//		return fileName[fileName.length - 1];
//	} // ExtractFilename(pathFile, cutExtension)
//
//	/// <summary>
//	/// Get directory of path+File, if only a path is given we will cut off
//	/// the last sub path!
//	/// </summary>
//	static public String GetDirectory(String pathFile)
//	{
//		if (pathFile == null || pathFile.isEmpty())
//			return "";
//
//		int i = pathFile.lastIndexOf("\\");
//		if (i >= 0 && i < pathFile.length())
//			// Return directory
//			return pathFile.substring(0, i);
//		// No sub directory found (parent of some dir is "")
//		return "";
//	} // GetDirectory(pathFile)
//
//	/// <summary>
//	/// Same as GetDirectory(): Get directory of path+File,
//	/// if only a path is given we will cut of the last sub path!
//	/// </summary>
//	static public String CutOneFolderOff(String path)
//	{
//		// GetDirectory does exactly what we need!
//		return GetDirectory(path);
//	} // CutOneFolderOff(path)
//
//	/// <summary>
//	/// Splits a path into all parts of its directories,
//	/// e.g. "maps\\sub\\kekse" becomes
//	/// {"maps\\sub\\kekse","maps\\sub","maps"}
//	/// </summary>
//	static public String[] SplitDirectories(String path)
//	{
//		ArrayList localList = new ArrayList();
//		localList.add(path);
//		do
//		{
//			path = CutOneFolderOff(path);
//			if (path.length() > 0)
//				localList.add(path);
//		} while (path.length() > 0);
//
////dothis		return (String[])localList.toArray(typeof(String));
//	} // SplitDirectories(path)
//
//	/// <summary>
//	/// Remove first directory of path (if one exists).
//	/// e.g. "maps\\mymaps\\hehe.map" becomes "mymaps\\hehe.map"
//	/// Also used to cut first folder off, especially useful for relative
//	/// paths. e.g. "maps\\test" becomes "test"
//	/// </summary>
//	static public String RemoveFirstDirectory(String path)
//	{
//		if (path == null || path.isEmpty())
//			return "";
//
//		int i = path.indexOf("\\");
//		if (i >= 0 && i < path.length())
//			// Return rest of path
//			return path.substring(i + 1);
//		// No first directory found, just return original path
//		return path;
//	} // RemoveFirstDirectory(path)

//	/// <summary>
//	/// Check if a folder is a direct sub folder of a main folder.
//	/// True is only returned if this is a direct sub folder, not if
//	/// it is some sub folder few levels below.
//	/// </summary>
//	static public boolean IsDirectSubfolder(String subfolder, String mainFolder)
//	{
//		// First check if subFolder is really a sub folder of mainFolder
//		if (subfolder != null &&
//			mainFolder != null &&
//			subfolder.startsWith(mainFolder))
//		{
//			// Same order?
//			if (subfolder.length() < mainFolder.length() + 1)
//				// Then it ain't a sub folder!
//				return false;
//			// Ok, now check if this is direct sub folder or some sub folder
//			// of mainFolder sub folder
//			String folder = subfolder.remove(0, mainFolder.length() + 1);
//			// Check if this is really a direct sub folder
//			for (int i = 0; i < folder.length; i++)
//				if (folder[i] == '\\')
//					// No, this is a sub folder of mainFolder sub folder
//					return false;
//			// Ok, this is a direct sub folder of mainFolder!
//			return true;
//		} // if (subFolder)
//
//		// Not even any sub folder!
//		return false;
//	} // IsDirectSubFolder(subFolder, mainFolder)
//
//	/// <summary>
//	/// Cut of extension, e.g. "hi.txt" becomes "hi"
//	/// </summary>
//	static public String CutExtension(String file)
//	{
//		if (String.IsNullOrEmpty(file))
//			return "";
//
//		int l = file.LastIndexOf('.');
//		if (l > 0)
//			return file.Remove(l, file.length - l);
//		return file;
//	} // CutExtension(file)
//
//	/// <summary>
//	/// Get extension (the stuff behind that '.'),
//	/// e.g. "test.bmp" will return "bmp"
//	/// </summary>
//	static public String GetExtension(String file)
//	{
//		if (file == null)
//			return "";
//		int l = file.LastIndexOf('.');
//		if (l > 0 && l < file.length)
//			return file.Remove(0, l + 1);
//		return "";
//	} // GetExtension(file)
//	#endregion
//
//	#region String splitting and getting it back together
//	/// <summary>
//	/// Performs basically the same job as String.Split, but does
//	/// trim all parts, no empty parts are returned, e.g.
//	/// "hi  there" returns "hi", "there", String.Split would return
//	/// "hi", "", "there".
//	/// </summary>
//	public static String[] SplitAndTrim(String text, char separator)
//	{
//		if (text == null)
//			return null;
//
//		ArrayList ret = new ArrayList();
//		String[] splitted = text.Split(new char[] { separator });
//		foreach (String s in splitted)
//			if (s.length > 0)
//				ret.Add(s);
//		return (String[])ret.ToArray(typeof(String));
//	} // SplitAndTrim(text, separator)
//
//	/// <summary>
//	/// Splits a multi line String to several Strings and
//	/// returns the result as a String array.
//	/// Will also remove any \r, \n or space character
//	/// at the end of each line!
//	/// </summary>
//	public static String[] SplitMultilineText(String text)
//	{
//		if (text == null)
//			throw new ArgumentNullException("text",
//				"Unable to execute method without valid text.");
//
//		ArrayList ret = new ArrayList();
//		// Supports any format, only \r, only \n, normal \n\r,
//		// crazy \r\n or even mixed \n\r with any format
//		String[] splitted1 = text.Split(new char[] { '\n' });
//		String[] splitted2 = text.Split(new char[] { '\r' });
//		String[] splitted =
//			splitted1.length >= splitted2.length ?
//		splitted1 : splitted2;
//
//		for (String s : splitted)
//		{
//			// Never add any \r or \n to the single lines
//			if (s.EndsWith("\r") ||
//				s.EndsWith("\n"))
//				ret.Add(s.substring(0, s.length - 1));
//			else if (s.startsWith("\n") ||
//				s.startsWith("\r"))
//				ret.Add(s.substring(1));
//			else
//				ret.Add(s);
//		} // foreach (s, splitted)
//
//		return (String[])ret.ToArray(typeof(String));
//	} // SplitMultiLineText(text)
//
//	/// <summary>
//	/// Build String from lines
//	/// </summary>
//	/// <param name="lines">Lines</param>
//	/// <param name="startLine">Start line</param>
//	/// <param name="startOffset">Start offset</param>
//	/// <param name="endLine">End line</param>
//	/// <param name="endOffset">End offset</param>
//	/// <param name="separator">Separator</param>
//	/// <returns>String</returns>
//	static public String BuildStringFromLines(
//		String[] lines,
//		int startLine, int startOffset,
//		int endLine, int endOffset,
//		String separator)
//	{
//		if (lines == null)
//			throw new ArgumentNullException("lines",
//				"Unable to execute method without valid lines.");
//
//		// Check if all values are in range (correct if not)
//		if (startLine >= lines.length)
//			startLine = lines.length - 1;
//		if (endLine >= lines.length)
//			endLine = lines.length - 1;
//		if (startLine < 0)
//			startLine = 0;
//		if (endLine < 0)
//			endLine = 0;
//		if (startOffset >= lines[startLine].length)
//			startOffset = lines[startLine].length - 1;
//		if (endOffset >= lines[endLine].length)
//			endOffset = lines[endLine].length - 1;
//		if (startOffset < 0)
//			startOffset = 0;
//		if (endOffset < 0)
//			endOffset = 0;
//
//		StringBuilder builder = new StringBuilder((endLine - startLine) * 80);
//		for (int lineNumber = startLine; lineNumber <= endLine; lineNumber++)
//		{
//			if (lineNumber == startLine)
//				builder.Append(lines[lineNumber].substring(startOffset));
//			else if (lineNumber == endLine)
//				builder.Append(lines[lineNumber].substring(0, endOffset + 1));
//			else
//				builder.Append(lines[lineNumber]);
//
//			if (lineNumber != endLine)
//				builder.Append(separator);
//		} // for (lineNumber)
//		return builder.ToString();
//	} // BuildStringFromLines(lines, startLine, startOffset)
//
//	static public String BuildStringFromLines(
//		String[] lines, String separator)
//	{
//		if (lines == null)
//			return "";
//
//		StringBuilder builder = new StringBuilder(lines.length * 80);
//		for (int lineNumber = 0; lineNumber < lines.length; lineNumber++)
//		{
//			builder.Append(lines[lineNumber]);
//			if (lineNumber != lines.length - 1)
//				builder.Append(separator);
//		} // for (lineNumber)
//		return builder.ToString();
//	} // BuildStringFromLines(lines, separator)
//
//	/// <summary>
//	/// Build String from lines
//	/// </summary>
//	/// <param name="lines">Lines</param>
//	/// <returns>String</returns>
//	static public String BuildStringFromLines(String[] lines)
//	{
//		return BuildStringFromLines(lines, NewLine);
//	} // BuildStringFromLines(lines)
//
//	/// <summary>
//	/// Build String from lines
//	/// </summary>
//	/// <param name="lines">Lines</param>
//	/// <param name="startLine">Start line</param>
//	/// <param name="endLine">End line</param>
//	/// <param name="separator">Separator</param>
//	/// <returns>String</returns>
//	static public String BuildStringFromLines(
//		String[] lines,
//		int startLine,
//		int endLine,
//		String separator)
//	{
//		if (lines == null)
//			return "";
//
//		// Check if all values are in range (correct if not)
//		if (startLine < 0)
//			startLine = 0;
//		if (endLine < 0)
//			endLine = 0;
//		if (startLine >= lines.length)
//			startLine = lines.length - 1;
//		if (endLine >= lines.length)
//			endLine = lines.length - 1;
//
//		StringBuilder builder = new StringBuilder((endLine - startLine) * 80);
//		for (int lineNumber = startLine; lineNumber <= endLine; lineNumber++)
//		{
//			builder.Append(lines[lineNumber]);
//			if (lineNumber != endLine)
//				builder.Append(separator);
//		} // for (lineNumber)
//		return builder.ToString();
//	} // BuildStringFromLines(lines, startLine, endLine)
//
//	/// <summary>
//	/// Cut modes
//	/// </summary>
//	public enum CutMode
//	{
//		Begin,
//		End,
//		BothEnds
//	} // enum CutMode
//
//	/// <summary>
//	/// Maximum String length
//	/// </summary>
//	/// <param name="originalText">Original text</param>
//	/// <param name="maxLength">Maximum length</param>
//	/// <param name="cutMode">Cut mode</param>
//	/// <returns>String</returns>
//	public static String MaxStringLength(String originalText,
//		int maxLength, CutMode cutMode)
//	{
//		if (originalText == null)
//			return "";
//		if (originalText.length <= maxLength)
//			return originalText;
//
//		if (cutMode == CutMode.Begin)
//			return originalText.substring(
//				originalText.length - maxLength, maxLength);
//		else if (cutMode == CutMode.End)
//			return originalText.substring(0, maxLength);
//		else // logic: if ( cutMode == CutModes.BothEnds )
//			return originalText.substring(
//				(originalText.length - maxLength) / 2, maxLength);
//	} // MaxStringLength(originalText, maxLength, cutMode)
//
//	/// <summary>
//	/// Get left part of everything to the left of the first
//	/// occurrence of a character.
//	/// </summary>
//	public static String GetLeftPartAtFirstOccurence(
//		String sourceText, char ch)
//	{
//		if (sourceText == null)
//			throw new ArgumentNullException("sourceText",
//				"Unable to execute this method without valid String.");
//
//		int index = sourceText.IndexOf(ch);
//		if (index == -1)
//			return sourceText;
//
//		return sourceText.substring(0, index);
//	} // GetLeftPartAtFirstOccurence(sourceText, ch)
//
//	/// <summary>
//	/// Get right part of everything to the right of the first
//	/// occurrence of a character.
//	/// </summary>
//	public static String GetRightPartAtFirstOccurrence(
//		String sourceText, char ch)
//	{
//		if (sourceText == null)
//			throw new ArgumentNullException("sourceText",
//				"Unable to execute this method without valid String.");
//
//		int index = sourceText.IndexOf(ch);
//		if (index == -1)
//			return "";
//
//		return sourceText.substring(index + 1);
//	} // GetRightPartAtFirstOccurrence(sourceText, ch)
//
//	/// <summary>
//	/// Get left part of everything to the left of the last
//	/// occurrence of a character.
//	/// </summary>
//	public static String GetLeftPartAtLastOccurrence(
//		String sourceText, char ch)
//	{
//		if (sourceText == null)
//			throw new ArgumentNullException("sourceText",
//				"Unable to execute this method without valid String.");
//
//		int index = sourceText.LastIndexOf(ch);
//		if (index == -1)
//			return sourceText;
//
//		return sourceText.substring(0, index);
//	} // GetLeftPartAtLastOccurrence(sourceText, ch)
//
//	/// <summary>
//	/// Get right part of everything to the right of the last
//	/// occurrence of a character.
//	/// </summary>
//	public static String GetRightPartAtLastOccurrence(
//		String sourceText, char ch)
//	{
//		if (sourceText == null)
//			throw new ArgumentNullException("sourceText",
//				"Unable to execute this method without valid String.");
//
//		int index = sourceText.LastIndexOf(ch);
//		if (index == -1)
//			return sourceText;
//
//		return sourceText.substring(index + 1);
//	} // GetRightPartAtLastOccurrence(sourceText, ch)
//
//	/// <summary>
//	/// Create password String
//	/// </summary>
//	/// <param name="originalText">Original text</param>
//	/// <returns>String</returns>
//	public static String CreatePasswordString(String originalText)
//	{
//		if (originalText == null)
//			throw new ArgumentNullException("originalText",
//				"Unable to execute this method without valid String.");
//
//		return new String('*', originalText.length);
//	} // CreatePasswordString(originalText)
//
//	/// <summary>
//	/// Helper function to convert letter to lowercase. Could someone
//	/// tell me the reason why there is no function for that in char?
//	/// </summary>
//	public static char ToLower(char letter)
//	{
//		return (char)letter.ToString().ToLower(
//			CultureInfo.InvariantCulture)[0];
//	} // ToLower(letter)
//
//	/// <summary>
//	/// Helper function to convert letter to uppercase. Could someone
//	/// tell me the reason why there is no function for that in char?
//	/// </summary>
//	public static char ToUpper(char letter)
//	{
//		return (char)letter.ToString().ToUpper(
//			CultureInfo.InvariantCulture)[0];
//	} // ToUpper(letter)
//
//	/// <summary>
//	/// Helper function to check if this is an lowercase letter.
//	/// </summary>
//	public static boolean IsLowercaseLetter(char letter)
//	{
//		return letter == ToLower(letter);
//	} // IsLowercaseLetter(letter)
//
//	/// <summary>
//	/// Helper function to check if this is an uppercase letter.
//	/// </summary>
//	public static boolean IsUppercaseLetter(char letter)
//	{
//		return letter == ToUpper(letter);
//	} // IsUppercaseLetter(letter)
//
//	/// <summary>
//	/// Helper function for SplitFunctionNameToWordString to detect
//	/// abbreviations in the function name
//	/// </summary>
//	private static int GetAbbreviationLengthInFunctionName(
//		String functionName, int startPos)
//	{
//		StringBuilder abbreviation = new StringBuilder();
//		// Go through String until we reach a lower letter or it ends
//		for (int pos = startPos; pos < functionName.length; pos++)
//		{
//			// Quit if its not an uppercase letter
//			if (StringHelper.IsUppercaseLetter(functionName[pos]) == false)
//				break;
//			// Else just add letter
//			abbreviation.Append(functionName[pos]);
//		} // for (pos)
//
//		// Abbreviation has to be at least 2 letters long.
//		if (abbreviation.length >= 2)
//		{
//			// If not at end of functionName, last letter belongs to next name,
//			// e.g. "TW" is not a abbreviation in "HiMrTWhatsUp",
//			// "AB" isn't one either in "IsABall",
//			// but "PQ" is in "PQList" and "AB" is in "MyAB"
//			if (startPos + abbreviation.length >= functionName.length)
//				// Ok, then return full abbreviation length
//				return abbreviation.length;
//			// Else return length - 1 because of next word
//			return abbreviation.length - 1;
//		} // if (abbreviation.length)
//
//		// No Abbreviation, just return 1
//		return 1;
//	} // GetAbbreviationLengthInFunctionName(functionName, startPos)
//
//	/// <summary>
//	/// Checks if letter is space ' ' or any punctuation (. , : ; ' " ! ?)
//	/// </summary>
//	public static boolean IsSpaceOrPunctuation(char letter)
//	{
//		return
//			letter == ' ' ||
//			letter == '.' ||
//			letter == ',' ||
//			letter == ':' ||
//			letter == ';' ||
//			letter == '\'' ||
//			letter == '\"' ||
//			letter == '!' ||
//			letter == '?' ||
//			letter == '*';
//	} // IsSpaceOrPunctuation(letter)

//	/// <summary>
//	/// Splits a function name to words, e.g.
//	/// "MakeDamageOnUnit" gets "Make damage on unit".
//	/// Will also detect abbreviation like TCP and leave them
//	/// intact, e.g. "CreateTCPListener" gets "Create TCP listener".
//	/// </summary>
//	public static String SplitFunctionNameToWordString(String functionName)
//	{
//		if (functionName == null ||
//			functionName.length == 0)
//			return "";
//
//		StringBuilder ret = new StringBuilder();
//		// Go through functionName and find big letters!
//		for (int pos = 0; pos < functionName.length; pos++)
//		{
//			char letter = functionName[pos];
//			// First letter is always big!
//			if (pos == 0 ||
//				pos == 1 && StringHelper.IsUppercaseLetter(functionName[1]) &&
//				StringHelper.IsUppercaseLetter(functionName[0]) ||
//				pos == 2 && StringHelper.IsUppercaseLetter(functionName[2]) &&
//				StringHelper.IsUppercaseLetter(functionName[1]) &&
//				StringHelper.IsUppercaseLetter(functionName[0]))
//				ret.Append(StringHelper.ToUpper(letter));
//			// Found uppercase letter?
//			else if (StringHelper.IsUppercaseLetter(letter) &&
//				//also support numbers and other symbols not lower/upper letter:
//				//StringHelper.IsLowercaseLetter(letter) == false &&
//				// But don't allow space or any punctuation (. , : ; ' " ! ?)
//				StringHelper.IsSpaceOrPunctuation(letter) == false &&
//				ret.ToString().EndsWith(" ") == false)
//			{
//				// Could be new word, but we have to check if its an abbreviation
//				int abbreviationLength = GetAbbreviationLengthInFunctionName(
//					functionName, pos);
//				// Found valid abbreviation?
//				if (abbreviationLength > 1)
//				{
//					// Then add it
//					ret.Append(" " + functionName.substring(pos, abbreviationLength));
//					// And advance pos (abbreviation is longer than 1 letter)
//					pos += abbreviationLength - 1;
//				} // if (abbreviationLength)
//				// Else just add new word (in lower letter)
//				else
//					ret.Append(" " + StringHelper.ToLower(letter));
//			} // else if
//			else
//				// Just add letter
//				ret.Append(letter);
//		} // for (pos)
//
//		return ret.ToString();
//	} // SplitFunctionNameToWordString(functionName)
//	#endregion
//
//	#region Remove character
//	/// <summary>
//	/// Remove character from text.
//	/// </summary>
//	/// <param name="text">Text</param>
//	/// <param name="characterToBeRemoved">Character to be removed</param>
//	public static void RemoveCharacter(ref String text,
//		char characterToBeRemoved)
//	{
//		if (text == null)
//			throw new ArgumentNullException("text",
//				"Unable to execute method without valid text.");
//
//		if (text.Contains(characterToBeRemoved.ToString()))
//			text = text.Replace(characterToBeRemoved.ToString(), "");
//	} // RemoveCharacter(text, characterToBeRemoved)
//	#endregion
//
//	#region Kb/mb name generator
//	/// <summary>
//	/// Write bytes, KB, MB, GB, TB message.
//	/// 1 KB = 1024 Bytes
//	/// 1 MB = 1024 KB = 1048576 Bytes
//	/// 1 GB = 1024 MB = 1073741824 Bytes
//	/// 1 TB = 1024 GB = 1099511627776 Bytes
//	/// E.g. 100 will return "100 Bytes"
//	/// 2048 will return "2.00 KB"
//	/// 2500 will return "2.44 KB"
//	/// 1534905 will return "1.46 MB"
//	/// 23045904850904 will return "20.96 TB"
//	/// </summary>
//	public static String WriteBigByteNumber(
//		long bigByteNumber, String decimalSeperator)
//	{
//		if (bigByteNumber < 0)
//			return "-" + WriteBigByteNumber(-bigByteNumber);
//
//		if (bigByteNumber <= 999)
//			return bigByteNumber + " Bytes";
//		if (bigByteNumber <= 999 * 1024)
//		{
//			double fKB = (double)bigByteNumber / 1024.0;
//			return (int)fKB + decimalSeperator +
//				((int)(fKB * 100.0f) % 100).ToString("00") + " KB";
//		} // if
//		if (bigByteNumber <= 999 * 1024 * 1024)
//		{
//			double fMB = (double)bigByteNumber / (1024.0 * 1024.0);
//			return (int)fMB + decimalSeperator +
//				((int)(fMB * 100.0f) % 100).ToString("00") + " MB";
//		} // if
//		// this is very big ^^ will not fit into int
//		if (bigByteNumber <= 999L * 1024L * 1024L * 1024L)
//		{
//			double fGB = (double)bigByteNumber / (1024.0 * 1024.0 * 1024.0);
//			return (int)fGB + decimalSeperator +
//				((int)(fGB * 100.0f) % 100).ToString("00") + " GB";
//		} // if
//		//if ( num <= 999*1024*1024*1024*1024 )
//		//{
//		double fTB = (double)bigByteNumber / (1024.0 * 1024.0 * 1024.0 * 1024.0);
//		return (int)fTB + decimalSeperator +
//			((int)(fTB * 100.0f) % 100).ToString("00") + " TB";
//		//} // if
//	} // WriteBigByteNumber(num, decimalSeperator)
//
//	/// <summary>
//	/// Write bytes, KB, MB, GB, TB message.
//	/// 1 KB = 1024 Bytes
//	/// 1 MB = 1024 KB = 1048576 Bytes
//	/// 1 GB = 1024 MB = 1073741824 Bytes
//	/// 1 TB = 1024 GB = 1099511627776 Bytes
//	/// E.g. 100 will return "100 Bytes"
//	/// 2048 will return "2.00 KB"
//	/// 2500 will return "2.44 KB"
//	/// 1534905 will return "1.46 MB"
//	/// 23045904850904 will return "20.96 TB"
//	/// </summary>
//	public static String WriteBigByteNumber(long bigByteNumber)
//	{
//		String decimalSeperator = CultureInfo.CurrentCulture.
//			NumberFormat.CurrencyDecimalSeparator;
//		return WriteBigByteNumber(bigByteNumber, decimalSeperator);
//	} // WriteBigByteNumber(num)
//	#endregion
//
//	#region Try parse methods that are not available on the XBox360!
//	/// <summary>
//	/// Is numeric float
//	/// </summary>
//	/// <param name="str">Str</param>
//	/// <returns>Bool</returns>
//	public static boolean IsNumericFloat(String str)
//	{
//		return IsNumericFloat(str, CultureInfo.InvariantCulture.NumberFormat);
//	} // IsNumericFloat(str)
//
//	/// <summary>
//	/// Allow only one decimal point, used for IsNumericFloat.
//	/// </summary>
//	/// <param name="str">Input String to check</param>
//	/// <param name="numberFormat">Used number format, e.g.
//	/// CultureInfo.InvariantCulture.NumberFormat</param>
//	/// <return>True if check succeeded, false otherwise</return>
//	private static boolean AllowOnlyOneDecimalPoint(String str,
//		NumberFormatInfo numberFormat)
//	{
//		char[] strInChars = str.ToCharArray();
//		bool hasGroupSeperator = false;
//		int decimalSeperatorCount = 0;
//		for (int i = 0; i < strInChars.length; i++)
//		{
//			if (numberFormat.CurrencyDecimalSeparator.IndexOf(strInChars[i]) == 0)
//			{
//				decimalSeperatorCount++;
//			} // if (numberFormat.CurrencyDecimalSeparator.IndexOf)
//
//			// has float group seperators  ?
//			if (numberFormat.CurrencyGroupSeparator.IndexOf(strInChars[i]) == 0)
//			{
//				hasGroupSeperator = true;
//			} // if (numberFormat.CurrencyGroupSeparator.IndexOf)
//		} // for (int)
//
//		if (hasGroupSeperator)
//		{
//			// If first digit is the group seperator or begins with 0,
//			// there is something wrong, the group seperator is used as a comma.
//			if (str.startsWith(numberFormat.CurrencyGroupSeparator) ||
//				strInChars[0] == '0')
//				return false;
//
//			// look only at the digits in front of the decimal point
//			String[] splittedByDecimalSeperator = str.Split(
//				numberFormat.CurrencyDecimalSeparator.ToCharArray());
//
//			#region Invert the digits for modulo check
//			//   ==> 1.000 -> 000.1  ==> only after 3 digits 
//			char[] firstSplittedInChars = splittedByDecimalSeperator[0].ToCharArray();
//			int arrayLength = firstSplittedInChars.length;
//			char[] firstSplittedInCharsInverted = new char[arrayLength];
//			for (int i = 0; i < arrayLength; i++)
//			{
//				firstSplittedInCharsInverted[i] =
//					firstSplittedInChars[arrayLength - 1 - i];
//			} // for (int)
//			#endregion
//
//			// group seperators are only allowed between 3 digits -> 1.000.000
//			for (int i = 0; i < arrayLength; i++)
//			{
//				if (i % 3 != 0 && numberFormat.CurrencyGroupSeparator.IndexOf(
//					firstSplittedInCharsInverted[i]) == 0)
//				{
//					return false;
//				} // if (i)
//			} // for (int)
//		} // if (hasGroupSeperator)
//		if (decimalSeperatorCount > 1)
//			return false;
//
//		return true;
//	} // AllowOnlyOneDecimalPoint(str, numberFormat)
//
//	/// <summary>
//	/// Checks if String is numeric float value
//	/// </summary>
//	/// <param name="str">Input String</param>
//	/// <param name="numberFormat">Used number format, e.g.
//	/// CultureInfo.InvariantCulture.NumberFormat</param>
//	/// <returns>True if str can be converted to a float,
//	/// false otherwise</returns>
//	public static boolean IsNumericFloat(String str,
//		NumberFormatInfo numberFormat)
//	{
//		// Can't be a float if String is not valid!
//		if (String.IsNullOrEmpty(str))
//			return false;
//
//		//not supported by Convert.ToSingle:
//		//if (str.EndsWith("f"))
//		//	str = str.substring(0, str.length - 1);
//
//		// Only 1 decimal point is allowed
//		if (AllowOnlyOneDecimalPoint(str, numberFormat) == false)
//			return false;
//
//		// + allows in the first,last,don't allow in middle of the String
//		// - allows in the first,last,don't allow in middle of the String
//		// $ allows in the first,last,don't allow in middle of the String
//		// , allows in the last,middle,don't allow in first char of the String
//		// . allows in the first,last,middle, allows in all the indexs
//		bool retVal = false;
//
//		// If String is just 1 letter, don't allow it to be a sign
//		if (str.length == 1 &&
//			"+-$.,".IndexOf(str[0]) >= 0)
//			return false;
//
//		for (int i = 0; i < str.length; i++)
//		{
//			// For first indexchar
//			char pChar =
//				//char.Parse(str.substring(i, 1));
//				Convert.ToChar(str.substring(i, 1));
//
//			if (retVal)
//				retVal = false;
//
//			if ((!retVal) && (str.IndexOf(pChar) == 0))
//			{
//				retVal = ("+-$.0123456789".IndexOf(pChar) >= 0) ? true : false;
//			} // if ()
//			// For middle characters
//			if ((!retVal) && (str.IndexOf(pChar) > 0) &&
//				(str.IndexOf(pChar) < (str.length - 1)))
//			{
//				retVal = (",.0123456789".IndexOf(pChar) >= 0) ? true : false;
//			} // if ()
//			// For last characters
//			if ((!retVal) && (str.IndexOf(pChar) == (str.length - 1)))
//			{
//				retVal = ("+-$,.0123456789".IndexOf(pChar) >= 0) ? true : false;
//			} // if ()
//
//			if (!retVal)
//				break;
//		} // for (int)
//
//		return retVal;
//	} // IsNumericFloat(str, numberFormat)
//
//	/// <summary>
//	/// Try to convert to float. Will not modify value if that does not work. 
//	/// This uses also always the invariant culture.
//	/// </summary>
//	/// <param name="value">Value</param>
//	/// <param name="textToConvert">Text to convert</param>
//	public static void TryToConvertToFloat(
//	ref float value, String textToConvert)
//{
//	TryToConvertToFloat(ref value, textToConvert,
//		System.Globalization.NumberFormatInfo.InvariantInfo);
//} // TryToConvertToFloat(value, textToConvert)
//
///// <summary>
///// Try to convert to float. Will not modify value if that does not work.
///// </summary>
///// <param name="value">Value</param>
///// <param name="textToConvert">Text to convert</param>
///// <param name="format">Format for converting</param>
//public static void TryToConvertToFloat(
//	ref float value, String textToConvert, NumberFormatInfo format)
//	{
//		// Basically the same as float.TryParse(), but faster!
//		if (IsNumericFloat(textToConvert, format))
//		{
//			value = Convert.ToSingle(textToConvert, format);
//		} // if (IsNumericFloat)
//	} // TryToConvertToFloat(value, textToConvert, format)
//
//	/// <summary>
//	/// Check if String is numeric integer. A decimal point is not accepted.
//	/// </summary>
//	/// <param name="str">String to check</param>
//	public static boolean IsNumericInt(String str)
//	{
//		// Can't be an int if String is not valid!
//		if (String.IsNullOrEmpty(str))
//			return false;
//
//		// Go through every letter in str
//		int strPos = 0;
//		for (char ch : str)
//		{
//			// Only 0-9 are allowed
//			if ("0123456789".IndexOf(ch) < 0 &&
//				// Allow +/- for first char
//				(strPos > 0 || (ch != '-' && ch != '+')))
//				return false;
//			strPos++;
//		} // foreach (ch in str)
//
//		// All fine, return true, this is a number!
//		return true;
//	} // IsNumericInt(str)


}
