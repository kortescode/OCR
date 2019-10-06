NAME		=	OCR.jar

SRC			=	./src/

JR			=	jar
JVM			=	java
JC			=	javac
RM			=	rm -f
PRINTF		=	printf

JRFLAGS		+=	cfm
JCFLAGS		+=	-cp ./lib/fannj-0.6.jar
JVMFLAGS	+=	-Djna.library.path=./lib/ -jar

MANIFEST	=	MANIFEST.MF

SRCS		=	$(SRC)*/*.java

CLS			=	$(SRCS:.java=.class)

$(NAME)		:	$(CLS)
				@$(JR) $(JRFLAGS) $(NAME) $(MANIFEST) -C $(SRC) .
				@$(PRINTF) "\033[32m*** Compilation of $(NAME) done ***\n\033[00m"

all			:	$(NAME)

run			:
				@$(JVM) $(JVMFLAGS) $(NAME) &

clean		:
				@$(RM) $(CLS)
				@printf "\033[31m*** Objects are removed ***\n\033[00m"

fclean		:	clean
				@$(RM) $(NAME)
				@printf "\033[31m*** Binary $(NAME) is removed ***\n\033[00m"

re			:	fclean all

.SUFFIXES 	:	.java .class

.java.class :
				$(JC) $(JCFLAGS) $(SRCS)

.PHONY		:	all run clean fclean re