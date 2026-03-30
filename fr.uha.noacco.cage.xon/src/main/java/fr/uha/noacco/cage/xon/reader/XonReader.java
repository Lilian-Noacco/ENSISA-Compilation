package fr.uha.noacco.cage.xon.reader;

import java.io.Reader;

import fr.uha.noacco.cage.xon.Parser;
import fr.uha.noacco.cage.xon.Scanner;
import fr.uha.noacco.cage.xon.model.xon.Node;
import fr.uha.page.runtime.ISymbol;
import fr.uha.page.runtime.SymbolFactory;

public class XonReader {

    public XonReader() {
    }

    public Node read(Reader reader) {
        try {
            SymbolFactory factory = new SymbolFactory();
            Scanner scanner = new Scanner(reader, factory);
            Parser parser = new Parser(scanner, factory);
            ISymbol root = parser.parse();
            return (Node) root.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}