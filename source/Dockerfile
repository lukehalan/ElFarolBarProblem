# Do not change the following line . It specifies the base image which
# will be downloaded when you build your image .

FROM pklehre/niso2019-lab5

# Add all the files you need for your submission into the Docker image ,
# e.g. source code , Java bytecode , etc. In this example , we assume your
# program is the Python code in the file abc123 .py. For simplicity , we
# copy the file to the /bin directory in the Docker image . You can add
# multiple files if needed .

ADD Constant.java /bin
ADD ElFarolBar.java /bin
ADD GA.java /bin
ADD Population.java /bin
ADD Run.java /bin
ADD State.java /bin
ADD Strategy.java /bin

# Install all the software required to run your code . The Docker image
# is derived from the Debian Linux distribution . You therefore need to
# use the apt -get package manager to install software . You can install
# e.g. java , python , ghc or whatever you need . You can also
# compile your code if needed .

# Note that Java and Python are already installed in the base image .

RUN javac ./bin/*.java -d ./bin/.

# The final line specifies your username and how to start your program .
# Replace abc123 with your real username and python /bin/ abc123 .py
# with what is required to start your program .

CMD ["-username", "mkh757", "-submission", "java -cp ./bin/ Run"]
