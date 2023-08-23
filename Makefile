#Nesan Naidoo
#15/08/2023
JAVAC=javac


SRCDIR=src
BINDIR=bin

SOURCES := $(wildcard $(SRCDIR)/clubSimulation/*.java)

CLASSES := $(SOURCES:$(SRCDIR)/%.java=$(BINDIR)/%.class)

all: $(CLASSES)

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d bin -cp -Xlint src/clubSimulation/*.java

clean:
	rm -rf $(BINDIR)

run:
		java -Xmx6g -cp $(BINDIR) clubSimulation.ClubSimulation 100 20 20 20