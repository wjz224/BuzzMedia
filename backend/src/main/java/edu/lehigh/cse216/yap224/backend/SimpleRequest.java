package edu.lehigh.cse216.yap224.backend;

/**
 * SimpleRequest provides a format for clients to present title and message 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequest {
    /**
     * The title being provided by the client.
     */
    public String mTitle;

    /**
     * The message being provided by the client.
     */
    public String mMessage;

    /**
     * The link attached to the post
     */
    public String mFileName;

    /**
     * The File attached to the post
     */
    public String mFile;
}