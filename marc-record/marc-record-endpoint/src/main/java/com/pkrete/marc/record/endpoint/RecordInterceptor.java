package com.pkrete.marc.record.endpoint;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;

public class RecordInterceptor extends StaxInInterceptor {
	private static final Logger LOG = Logger.getLogger(RecordInterceptor.class
			.getName());

	public RecordInterceptor() {
		super(Phase.RECEIVE);
	}

	public void handleMessage(Message message) {
		InputStream is = message.getContent(InputStream.class);
		if (is != null) {
			CachedOutputStream bos = new CachedOutputStream();
			try {
				Scanner scanner = new Scanner(is, "UTF-8");
				if (scanner != null && scanner.hasNext()) {
					String inputStreamString = scanner.useDelimiter("\\A").next();
					inputStreamString = inputStreamString
							.replaceAll("<record>",
									"<record xmlns=\"http://www.loc.gov/MARC21/slim\">");
					inputStreamString = inputStreamString
							.replaceAll("<collection>",
									"<collection xmlns=\"http://www.loc.gov/MARC21/slim\">");

					is = new ByteArrayInputStream(
							inputStreamString.getBytes("UTF-8"));
					scanner.close();
				} else {
					IOUtils.copy(is, bos);
					bos.flush();
					is = bos.getInputStream();
				}
				message.setContent(InputStream.class, is);
				is.close();
				bos.close();

			} catch (Exception e) {
				throw new Fault(e);
			}
		}
	}
}
