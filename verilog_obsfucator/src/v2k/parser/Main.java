

package v2k.parser;

import  java.io.*;
import  java.util.*;
import  v2k.util.ProcArgs;
import  v2k.util.ProcArgs.Spec.EType;
import  v2k.util.Pair;
import  v2k.parser.tree.ASTreeBase;
import  static v2k.util.Utils.asEmpty;
import  static v2k.util.Utils.nl;
import  static v2k.util.Utils.fatal;
import  static v2k.message.MessageMgr.message;

/**
 *
 * @author karl
 */
public class Main
{
    public Main(String argv[]) {
        try {
            m_args = new Args(argv);
            parse();
            dumpSymbols();
        }
        catch (Exception ex) {
            fatal(ex);
        }
    }
    protected Main() {
        //nothing
    }

    public static void main(String argv[])  {
        new Main(argv);
    }
    private final void dumpSymbols() throws Exception {
        if (0 >= getIntProperty("dumpSymbols")) {
            return;
        }
        String fn = getStringProperty("symbolsFname");
        PrintStream os = new PrintStream(new FileOutputStream(fn));
        message('I', "FILE-4", fn);
        v2k.parser.tree.symbol.Debug.dumpSymbols(os);
        os.close();
    }
    private static void usageErr(String msg) {
        System.err.println("Error: " + msg);
        usage();
        System.exit(1);
    }

    private static void usage() {
        final String progName = "v2kparse";
        final String usage =
              "Usage: " + progName + " [options] infile..." + nl()
            + nl()
            + "Options:" + nl()
            + nl()
            + "  -I <dir>    Add <dir> (must be readable) to `include file search path." + nl()
            + "" + nl()
            + "  -D name     Predefine name as a macro with value 1." + nl()
            + "" + nl()
            + "  -D name=defn    Predefine name as a macro with value defn." + nl()
            ;
        System.err.println(usage);
    }
    /**
     * Allow subclass to define the ASTreeBase to use.
     *
     * @return the tree to use
     */
    protected ASTreeBase getTree() {
        return null;
    }

    private Parser  m_parser = null;

    private void parse() {
        setAntlrLineFormatter();    //we do in Message.
        m_srcFiles = m_args.getOptVals(".");
        if (1 > m_srcFiles.size()) {
            usageErr("Must specify at least one \"infile\".");
        }
        List<String> inclDirs = m_args.getOptVals("-I");
        List<Pair<String,String> > defs = Pair.factory(m_args.getOptVals("-D"));
        ASTreeBase ast = getTree();
        m_parser = (null == ast) ? new Parser() : new Parser(ast);
        m_parser.parse(m_srcFiles, inclDirs, defs);
    }
    public Parser getParser() {
        return m_parser;
    }

/**/
    private static void setAntlrLineFormatter() {
        antlr.FileLineFormatter.setFormatter(
            new antlr.FileLineFormatter() {
                public String getFormatString(String fileName,
                                              int line, int column) {
                    return "";
                }
            }
        );
    }
/**/

    private Args            m_args;
    private List<String>    m_srcFiles;

    private static class Args extends ProcArgs {
        public Args(final String argv[]) throws ArgException {
            this.add(new ProcArgs.Spec("-I*", EType.eReadableDir))
                .add(new ProcArgs.Spec("-D*", EType.eString))
                .add(new ProcArgs.Spec(".+", EType.eReadableFile))  //filenames
                ;
            m_args = parse(argv);
        }

        public boolean hasOpt(String opt) {
            return (null != m_args) ? m_args.containsKey(opt) : false;
        }

        public String getOptVal(String opt) {
            String rval = null;
            List<String> vals = getOptVals(opt);
            if (null != vals) {
                assert(1 == vals.size());
                rval = vals.get(0);
            }
            return rval;
        }

        public List<String> getOptVals(String opt) {
            return asEmpty(m_args.get(opt), new LinkedList<String>());
        }

        private Map<String, List<String>> m_args;
    }

    /**
     * Configure properties used to control parser, debug, ...
     */
    private static class Config extends Properties {
        Config() {
            super(m_defaults);
        }
        private static Properties m_defaults = new Properties();
        static {
            m_defaults.setProperty("dumpSymbols", "0");
            m_defaults.setProperty("symbolsFname", "symbols.txt");
            m_defaults.setProperty("enableSSI", "0");
        }
    }

    private static Config   m_config = new Config();

    public static final int getIntProperty(String nm) {
        int rval = 0;
        String str = m_config.getProperty(nm);
        if (null != str) {
            rval = Integer.parseInt(str);
        }
        return rval;
    }
    public static final String getStringProperty(String nm) {
        return m_config.getProperty(nm);
    }
}
