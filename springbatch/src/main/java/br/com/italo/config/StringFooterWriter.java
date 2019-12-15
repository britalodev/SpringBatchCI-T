package br.com.italo.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

class StringFooterWriter implements FlatFileFooterCallback {

	private final String footer;

	StringFooterWriter(String footer) {
		this.footer = footer;
	}

	public void writeHeader(Writer writer) throws IOException {
		
	}

	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write(footer);	
	}
}
