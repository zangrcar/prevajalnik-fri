JAVA            = java
ANTLRDIR        = ../lib/antlr-4.13.2-complete.jar
XSLTPROC	= xsltproc
RM              = 'rm' -fr

PHASE		?=

ifeq ($(strip $(PHASE)),)
TARGETPHASE	?= all
LOGGEDPHASE	?= none
else
TARGETPHASE	?= $(PHASE)
LOGGEDPHASE	?= $(PHASE)
endif

NUMREGS ?= 26

TESTS := $(basename $(wildcard test*.p26))
ERRORS := $(basename $(wildcard err*.p26))

.PHONY	: %
%	: %.p26
	$(RM) -fr $@-*.xml $@-*.html
	if $(JAVA) -cp ../bin:../src:$(ANTLRDIR) prev26lang.Compiler --xsl=../lib/xsl/ --logged-phase=$(LOGGEDPHASE) --target-phase=$(TARGETPHASE) --num-regs=$(NUMREGS) $(OPTIONS) $< ; then for XML in `'find' . -iname $@"-*.xml"` ; do $(XSLTPROC) $$XML -o `basename $$XML xml`html; done ; fi

.PHONY  : clean
clean   :
	$(RM) *~
	$(RM) *xml *html


.PHONY: alltests
alltests: $(TESTS)

.PHONY: errors
errors: $(ERRORS)
