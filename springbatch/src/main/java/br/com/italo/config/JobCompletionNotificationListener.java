package br.com.italo.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

import br.com.italo.enums.EvenOrOddEnum;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Autowired
	public JobCompletionNotificationListener() {
	}

	@SuppressWarnings("resource")
	public void afterJob(JobExecution jobExecution) {
		try {
			if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
				log.info("!!! JOB FINISHED! Time to verify the results");

				List<String[]> linhas = new ArrayList<String[]>();

				linhas.add(new String[] { "TOTAL," + EvenOrOddEnum.IMPAR + "," + CSVtoCSVConfig.SOMAIMPAR + ","
						+ CSVtoCSVConfig.QUANTIDADEIMPAR });
				linhas.add(new String[] { "TOTAL," + EvenOrOddEnum.PAR + "," + CSVtoCSVConfig.SOMAPAR + ","
						+ CSVtoCSVConfig.QUANTIDADEPAR });

				Writer writer;
				writer = Files.newBufferedWriter(Paths.get("src\\main\\resources\\output-extra.csv"));
				CSVWriter csvWriter = new CSVWriter(writer);
				
				FileReader fReader = new FileReader(new File("src\\main\\resources\\output.csv"));
				BufferedReader buffReader = new BufferedReader(fReader);
				String line = null;
				while((line = buffReader.readLine()) != null) {
					String[] split = line.split(",");
					csvWriter.writeNext(split);
				    System.out.println("Linha: " + line);  
				}
				
				buffReader.close();  
				fReader.close();
				csvWriter.writeAll(linhas);
				csvWriter.flush();
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
