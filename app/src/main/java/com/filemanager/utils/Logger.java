package com.filemanager.utils;

import java.util.HashMap;

import android.util.Log;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public final class Logger {

	private static HashMap<String, Integer> tagMap = new HashMap<String, Integer>();
	
	public static final int LOG_IS_SWITCHED_OFF = Log.ASSERT + 1;//8;
    public static final int LOG_IS_SWITCHED_ON = Log.VERBOSE - 1;//1;
    public static final int LOG_MAX_LEN = 4000;
    private static boolean logEnabled = true;


    
    /**
     * Send an INFO log message
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */    
    public static int i (String tag, String msg) {
        int level = Log.INFO;
        if (!logEnabled || Logger.getLogLevel(tag) > level) {
            return LOG_IS_SWITCHED_OFF;
        } else {
            
            int remainder = msg.length();
            if (remainder<=LOG_MAX_LEN) {
                Log.i(tag, msg); //to avoid extra string copy in the most common case
            } else {
                int start = 0;
                int end = 0;
                do {
                    end += (remainder>LOG_MAX_LEN)?(LOG_MAX_LEN):remainder;
                    remainder -= (end-start); 
                    Log.i(tag, msg.substring(start,end));
                    start += LOG_MAX_LEN;
                } while(remainder > 0);
            }
        }
        return level;
    }

    /**
     * Send a INFO log message and log the exception. Since the log level is
     * below ERROR, only the exception's main message is logged.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param tr
     *            - An exception to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int i (String tag, Throwable tr) {
        return i(tag, tr.toString());
    }

    /**
     * Dumps the buffer to the log at INFO level
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The title of the data.
     * @param buffer
     *            - A buffer to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int i (String tag, String msg, byte[] buffer) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.INFO) {            
            return Logger.i(tag, dumpBytes(msg, buffer));
        }
        return LOG_IS_SWITCHED_OFF;
    }

    /**
     * Send a VERBOSE log message.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int v (String tag, String msg) {
        int level = Log.VERBOSE;
        if (!logEnabled || Logger.getLogLevel(tag) > level) {
        	
            return LOG_IS_SWITCHED_OFF;
        } else {
                       
            int remainder = msg.length();
            if (remainder<=LOG_MAX_LEN) {
                Log.v(tag, msg); //to avoid extra string copy in the most common case
            } else {
                int start = 0;
                int end = 0;
                do {
                    end += (remainder>LOG_MAX_LEN)?(LOG_MAX_LEN):remainder;
                    remainder -= (end-start); 
                    Log.v(tag, msg.substring(start,end));
                    start += LOG_MAX_LEN;
                } while(remainder > 0);
            }
        }
        return level;
    }

    /**
     * Send a VERBOSE log message and log the exception. Since the log level is
     * below ERROR, only the exception's main message is logged.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int v (String tag, Throwable tr) {
        return v(tag, tr.toString());
    }

    /**
     * Dumps the buffer to the log at VERBOSE level
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The title of the data.
     * @param buf
     *            - A buffer to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int v (String tag, String msg, byte[] buffer) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.VERBOSE) {
        	
            return Logger.v(tag, dumpBytes(msg, buffer));
        }
        return LOG_IS_SWITCHED_OFF;
    }

    /**
     * Send a WARN log message.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int w (String tag, String msg) {
        int level = Log.WARN;
        if (!logEnabled || Logger.getLogLevel(tag) > level) {
        	
            return LOG_IS_SWITCHED_OFF;
        } else {
            
            int remainder = msg.length();
            if (remainder<=LOG_MAX_LEN) {
                Log.w(tag, msg); //to avoid extra string copy in the most common case
            } else {
                int start = 0;
                int end = 0;
                do {
                    end += (remainder>LOG_MAX_LEN)?(LOG_MAX_LEN):remainder;
                    remainder -= (end-start); 
                    Log.w(tag, msg.substring(start,end));
                    start += LOG_MAX_LEN;
                } while(remainder > 0);
            }
        }
        return level;
    }

    /**
     * Send a WARN log message and log the exception. Since the log level is
     * below ERROR, only the exception's main message is logged.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param tr
     *            - An exception to log.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int w (String tag, Throwable tr) {
    	
        return w(tag, tr.toString());
    }

    /**
     * Dumps the buffer to the log at WARN level
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The title of the data.
     * @param buf
     *            - A buffer to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int w (String tag, String msg, byte[] buffer) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.WARN) {  
        	
            return Logger.w(tag, dumpBytes(msg, buffer));
        }
        return LOG_IS_SWITCHED_OFF;
    }

    /**
     * Send a DEBUG log message.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int d (String tag, String msg) {
        int level = Log.DEBUG;
        if (!logEnabled || Logger.getLogLevel(tag) > level) {
            return LOG_IS_SWITCHED_OFF;
        } else {
           
            int remainder = msg.length();
            if (remainder<=LOG_MAX_LEN) {
                Log.d(tag, msg); //to avoid extra string copy in the most common case
            } else {
                int start = 0;
                int end = 0;
                do {
                    end += (remainder>LOG_MAX_LEN)?(LOG_MAX_LEN):remainder;
                    remainder -= (end-start); 
                    Log.d(tag, msg.substring(start,end));
                    start += LOG_MAX_LEN;
                } while(remainder > 0);
            }
        }
        return level;
    }

    /**
     * Send a DEBUG log message and log the exception. Since the log level is
     * below ERROR, only the exception's main message is logged.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param tr
     *            - An exception to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int d (String tag, Throwable tr) {
        return d(tag, tr.toString());
    }

    /**
     * Dumps the buffer to the log at DEBUG level
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The title of the data.
     * @param buf
     *            - A buffer to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int d (String tag, String msg, byte[] buffer) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.DEBUG) {
           
            return Logger.d(tag, dumpBytes(msg, buffer));
        }
        return LOG_IS_SWITCHED_OFF;
    }

    /**
     * Send an ERROR log message.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The message you would like logged.
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int e (String tag, String msg) {
        int level = Log.ERROR;
        if (!logEnabled || Logger.getLogLevel(tag) > level) {
            return LOG_IS_SWITCHED_OFF;
        } else {
           
            int remainder = msg.length();
            if (remainder<=LOG_MAX_LEN) {
                Log.e(tag, msg); //to avoid extra string copy in the most common case
            } else {
                int start = 0;
                int end = 0;
                do {
                    end += (remainder>LOG_MAX_LEN)?(LOG_MAX_LEN):remainder;
                    remainder -= (end-start); 
                    Log.e(tag, msg.substring(start,end));
                    start += LOG_MAX_LEN;
                } while(remainder > 0);
            }
        }
        return level;
    }

    /**
     * Send a ERROR log message and log the exception. The full stack trace
     * is logged.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param tr
     *            - An exception to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int e (String tag, Throwable tr) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.ERROR) {
           
            return Log.e(tag, "", tr);
        }
        return LOG_IS_SWITCHED_OFF;
    }

    /**
     * Dumps the buffer to the log at ERROR level
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            - The title of the data.
     * @param buf
     *            - A buffer to log
     * @return - Whether or not that this is allowed to be logged.
     */
    public static int e (String tag, String msg, byte[] buffer) {
        if (logEnabled && Logger.getLogLevel(tag) <= Log.ERROR) {
           
            return Logger.e(tag, dumpBytes(msg, buffer));
        }
        return LOG_IS_SWITCHED_OFF;
    }
 

    /**
     * This function gets log level.
     * 
     * @param tag
     *            - Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @return - Log level for given tag.
     */
    public static int getLogLevel (String tag) {
        if (tagMap.containsKey(tag)) {
            return tagMap.get(tag);
        }
        return LOG_IS_SWITCHED_ON;
    }

   
    private static String dumpBytes ( String title, byte[] bytes) {

        String text = "";
        
        if(title!=null) {
            text += title;
        }
        
        if (bytes == null) {
            text += ":null";
            return text;
        }

        final int lineLength = 16;
        int inLentgh = bytes.length;

        text += "("+inLentgh+"):\n";
        
        for (int i = 0; i < inLentgh; i+=lineLength) {
            
            text += String.format("%06x:", i);
            for (int j=0; j<lineLength; j++) {
                if (i+j < inLentgh){
                    text += String.format("%02x ", bytes[i+j]&0xFF);
                 }
                else {
                    text += "   ";
                }
            }
            
            text += " ";
            for ( int j=0; j<lineLength; j++) {
                if (i+j < inLentgh){
                    char ch = (char)(bytes[i+j]&0xFF);
                    text +=  String.format("%c", (ch>=32 && ch<=126) ? ch : '.');
                }
            }
            text += "\n";
        }
        return text;
    }

	
}
