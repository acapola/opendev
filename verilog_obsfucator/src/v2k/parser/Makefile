VERS       := 2.7.7
OS		   := $(shell uname)
ifeq "$(findstring CYGWIN,${OS})" ""
#JARS       := /usr/share/antlr/antlr-${VERS}/antlr/antlr.jar
ANTLR_EXEC := antlr
else
JARS       := c:/antlr/antlr-${VERS}/antlr/antlr.jar
ANTLR_EXEC := java -cp ${JARS} antlr.Tool
endif
ANTLR_ODIR := .

GRAM  := Vlog
GRAMF := ${GRAM}.g
GRAMF2:= ${GRAM}pp.g

OPTS := -diagnostic

#.PHONY: debug
ifdef DBG
OPTS += -debug
endif

.PHONY: all

all: ${ANTLR_ODIR}/${GRAM}Parser.java ${ANTLR_ODIR}/${GRAM}ppLexer.java

${ANTLR_ODIR}/${GRAM}Parser.java : ${GRAMF}
	- ${ANTLR_EXEC} -diagnostic -o ${ANTLR_ODIR} ${<} 2>/dev/null
	${ANTLR_EXEC} -o ${ANTLR_ODIR} ${<}

##mkastree ${ANTLR_ODIR}/${GRAM}Parser.java

${ANTLR_ODIR}/${GRAM}ppLexer.java : ${GRAMF2}
	${ANTLR_EXEC} -o ${ANTLR_ODIR} ${<}

OUTFILES += ${GRAM}Parser.java
OUTFILES += ${GRAM}ParserTokenTypes.java
OUTFILES += ${GRAM}ParserTokenTypes.txt
OUTFILES += ${GRAM}Parser.txt
OUTFILES += ${GRAM}Parser.smap
OUTFILES += ${GRAM}Lexer.java
OUTFILES += ${GRAM}Lexer.txt
OUTFILES += ${GRAM}Lexer.smap
OUTFILES += ${GRAM}ppLexer.java
OUTFILES += ${GRAM}ppLexer.txt
OUTFILES += ${GRAM}ppLexer.smap

.PHONY : clean
clean:
	- rm -f $(OUTFILES:%=${ANTLR_ODIR}/%)
