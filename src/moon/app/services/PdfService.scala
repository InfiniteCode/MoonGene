//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package services

import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.{StringReader, StringWriter, ByteArrayOutputStream}
import org.w3c.tidy.Tidy

/*
  PdfService helper to create PDF documents from HTML
 */
object PdfService {
  def generatePdf(content: String) : Array[Byte] = {
    val os = new ByteArrayOutputStream()

    val renderer = new ITextRenderer()
    renderer.setDocumentFromString(tidify(content))
    renderer.layout()
    renderer.createPDF(os)

    os.toByteArray;
  }

  def tidify(body: String) : String = {
    val tidy = new Tidy()
    tidy.setXHTML(true)
    val writer = new StringWriter()
    tidy.parse(new StringReader(body), writer)

    writer.getBuffer().toString()
  }
}