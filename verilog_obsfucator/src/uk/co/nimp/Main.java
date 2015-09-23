package uk.co.nimp;

import  java.util.*;
import  v2k.parser.tree.*;

public class Main extends v2k.parser.Main {

    static class Tree extends ParseTree {
        @Override
        public void moduleDeclaration(ModuleDeclaration mdecl) {
            System.out.println(mdecl.getModName());

            if((mdecl.getModName().equals("sha2_sec_ti2_rm0"))||
                    (mdecl.getModName().equals("sha2_sec_ti2_rm1"))     ) {
                HashMap<String, PortIdent> ports = mdecl.getPorts();
                m_ports.add(ports);
            }
        }

        @Override
        public PartSelect partSelect(PartSelect ps, RangeExpression rexp) {
            System.out.println(rexp);
            return super.partSelect(ps, rexp);
        }

        @Override
        public Statement statement(NonBlockingAssign ba) {
            //System.out.println(ba);

            return super.statement(ba);
        }
    }
    static List<HashMap<String,PortIdent>>  m_ports = new LinkedList<HashMap<String,PortIdent>>();

    @Override
    protected ASTreeBase getTree() {
        return new Tree();
    }

    private Main(String argv[]) {

        super(argv);
    }

    private void process() {
        if(2==m_ports.size()) {
            HashMap<String, PortIdent>
                    before = m_ports.get(0),
                    after = m_ports.get(1);

            for (String n : after.keySet()) {
                if (false == before.containsKey(n)) {
                    System.out.println("port \"" + n + "\": added");
                }
            }
            for (String n : before.keySet()) {
                if (false == after.containsKey(n)) {
                    System.out.println("port \"" + n + "\": deleted");
                }
            }
        }
    }

    public static void main(String argv[]) {

        Main m = (new Main(argv));
        m.process();

    }
}
