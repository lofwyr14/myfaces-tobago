/*
 * Copyright (c) 2004 Atanion GmbH, Germany
 * All rights reserved. Created 26.08.2004 10:56:38.
 * $Id: MockResponseWriter.java,v 1.1.1.1 2004/08/27 13:02:11 lofwyr Exp $
 */
package com.atanion.tobago.mock.faces;

import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import java.io.Writer;
import java.io.IOException;

public class MockResponseWriter extends ResponseWriter {


    public MockResponseWriter(Writer writer, String encoding) {
        this.writer = writer;
        this.encoding = encoding;
    }


    private Writer writer;
    private String encoding;


    // ---------------------------------------------------------- Writer Methods


    public void close() throws IOException {
        writer.close();
    }


    public void flush() throws IOException {
        writer.flush();
    }


    public void write(char c) throws IOException {
        writer.write(c);
    }


    public void write(char cbuf[], int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }


    public void write(int c) throws IOException {
        writer.write(c);
    }


    public void write(String s) throws IOException {
        writer.write(s);
    }


    public void write(String s, int off, int len) throws IOException {
        writer.write(s, off, len);
    }


    // --------------------------------------------------- ResponseWrter Methods

    public String getContentType() {
        return "text/html";
    }

    public String getCharacterEncoding() {
        return (this.encoding);
    }


    public void startDocument() throws IOException {
        throw new UnsupportedOperationException();
    }


    public void endDocument() throws IOException {
        throw new UnsupportedOperationException();
    }


    public void startElement(String name,
			     UIComponent component) throws IOException {
        throw new UnsupportedOperationException();
    }


    public void endElement(String name) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void writeAttribute(String name, Object value,
			       String componentPropertyName) throws IOException {
        throw new UnsupportedOperationException();
    }


    public void writeURIAttribute(String name, Object value, String componentPropertyName) throws IOException {
        throw new UnsupportedOperationException();
    }


    public void writeComment(Object comment) throws IOException {
        throw new UnsupportedOperationException();
    }


    public void writeText(Object text, String componentProperty) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void writeText(char text[], int off, int len) throws IOException {
        throw new UnsupportedOperationException();
    }


    public ResponseWriter cloneWithWriter(Writer writer) {
        throw new UnsupportedOperationException();
    }


}
