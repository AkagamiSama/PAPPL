/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 * Exception levée quand un fichier de grammaire n'est pas au format attendu.
 * @author akagami
 */
public class InvalidFileException extends Exception {

    /**
     * Creates a new instance of <code>InvalidFileException</code> without
     * detail message.
     */
    public InvalidFileException() {
    }

    /**
     * Constructs an instance of <code>InvalidFileException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidFileException(String msg) {
        super(msg);
    }
}
