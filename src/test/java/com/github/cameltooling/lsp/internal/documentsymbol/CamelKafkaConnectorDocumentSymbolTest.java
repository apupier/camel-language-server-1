/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.documentsymbol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.jupiter.api.Test;

import com.github.cameltooling.lsp.internal.AbstractCamelLanguageServerTest;
import com.github.cameltooling.lsp.internal.CamelLanguageServer;

class CamelKafkaConnectorDocumentSymbolTest extends AbstractCamelLanguageServerTest {

	@Test
	void testConnectorClass() throws Exception {
		String text = "connector.class=org.test.TestClass";
		CamelLanguageServer languageServer = initializeLanguageServer(text, ".properties");
		List<Either<SymbolInformation, DocumentSymbol>> symbols = getDocumentSymbolFor(languageServer).get();
		DocumentSymbol documentSymbolForConnectorClass = symbols.get(0).getRight();
		assertThat(documentSymbolForConnectorClass.getName()).isEqualTo("TestClass");
		assertThat(documentSymbolForConnectorClass.getRange()).isEqualTo(new Range(new Position(0, 0), new Position(0, text.length())));
	}
	
	@Test
	void testCamelSinkPath() throws Exception {
		String text = "connector.class=org.test.TestClass\n"
					+ "camel.sink.path.aMediumOption=test";
		CamelLanguageServer languageServer = initializeLanguageServer(text, ".properties");
		List<Either<SymbolInformation, DocumentSymbol>> symbols = getDocumentSymbolFor(languageServer).get();
		assertThat(symbols).hasSize(1);
		DocumentSymbol documentSymbolForCamelSinkPath = symbols.get(0).getRight().getChildren().get(0);
		assertThat(documentSymbolForCamelSinkPath.getName()).isEqualTo("aMediumOption");
		assertThat(documentSymbolForCamelSinkPath.getRange()).isEqualTo(new Range(new Position(1, 0), new Position(1, "camel.sink.path.aMediumOption=test".length())));
	}
	
}
