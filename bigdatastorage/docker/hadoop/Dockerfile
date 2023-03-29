FROM centos:7

RUN yum -y update
RUN yum install -y epel-release 
RUN yum install -y java-1.8.0-openjdk
RUN yum -y install net-tools wget openssh-server openssh-clients openssh-askpass supervisor

USER root

#Versions
ENV HBASE_VERSION 2.3.7
ENV HADOOP_VER 3.0.0
ENV THRIFT_SERVER_VERSION 1.0

#DownLoad Hadoop, Hive, Spark

RUN curl -s https://archive.apache.org/dist/hadoop/common/hadoop-$HADOOP_VER/hadoop-$HADOOP_VER.tar.gz | tar -xvz -C /usr/local/
RUN cd /usr/local && ln -s hadoop-$HADOOP_VER hadoop

ENV HADOOP_PREFIX /usr/local/hadoop
ENV HADOOP_HOME /usr/local/hadoop
ENV HADOOP_MAPRED_HOME ${HADOOP_HOME}
ENV HADOOP_COMMON_HOME ${HADOOP_HOME}
ENV HADOOP_HDFS_HOME ${HADOOP_HOME}
ENV YARN_HOME ${HADOOP_HOME}

ENV HADOOP_COMMON_LIB_NATIVE_DIR ${HADOOP_HOME}/lib/native
ENV HADOOP_OPT "-Djava.library.path=$HADOOP_PREFIX/lib/native"

ENV PATH $HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH

ENV JAVA_HOME /usr/lib/jvm/jre-1.8.0-openjdk
ENV HDFS_NAMENODE_USER root
ENV HDFS_DATANODE_USER root
ENV HDFS_SECONDARYNAMENODE_USER root
ENV YARN_RESOURCEMANAGER_USER root
ENV YARN_NODEMANAGER_USER root
ENV YARN_PROXYSERVER_USER root
ENV HADOOP_OPTS "-Djava.library.path=$HADOOP_HOME/lib"

RUN ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa
RUN cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys
RUN chmod 644 /root/.ssh/authorized_keys
RUN yum -y install dos2unix

WORKDIR /
ADD supervisord.conf supervisord.conf

ADD start-hadoop.sh start-hadoop.sh
RUN chmod a+x start-hadoop.sh 

RUN ssh-keygen -q -t rsa -N '' -f /etc/ssh/ssh_host_rsa_key
RUN ssh-keygen -q -t dsa -N '' -f /etc/ssh/ssh_host_ecdsa_key
RUN ssh-keygen -q -t ed25519 -N '' -f /etc/ssh/ssh_host_ed25519_key
RUN dos2unix start-hadoop.sh

ENTRYPOINT ["supervisord", "-c", "/supervisord.conf"]
