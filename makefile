JFLAGS = -g
JC = javac
JAVA = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	FileHandler.java \
    Admin.java \
    Student.java \
    Teacher.java \
    User.java

default: classes

classes: $(CLASSES:.java=.class)

run: $(CLASSES:.java=.class)
	$(JAVA) User

clean:
	$(RM) *.class
