FROM centos:7

RUN yum install -y java-1.8.0-openjdk
RUN yum -y install net-tools
RUN yum -y install wget

USER root

#Versions
ENV SPARK_VERSION 3.0.1
ENV HADOOP_PROFILE 2.7
ENV HIVE_VERSION 2.3.7
ENV HADOOP_VER 3.0.0
ENV THRIFT_SERVER_VERSION 1.0


#DownLoad Hadoop, Hive, Spark
RUN curl -s https://archive.apache.org/dist/spark/spark-$SPARK_VERSION/spark-$SPARK_VERSION-bin-hadoop$HADOOP_PROFILE.tgz | tar -xvz -C /usr/local/
RUN cd /usr/local && ln -s spark-$SPARK_VERSION-bin-hadoop$HADOOP_PROFILE spark

RUN curl -s https://archive.apache.org/dist/hive/hive-$HIVE_VERSION/apache-hive-$HIVE_VERSION-bin.tar.gz | tar -xvz -C /usr/local/
RUN cd /usr/local && ln -s apache-hive-$HIVE_VERSION-bin hive

RUN curl -s https://archive.apache.org/dist/hadoop/common/hadoop-$HADOOP_VER/hadoop-$HADOOP_VER.tar.gz | tar -xvz -C /usr/local/
RUN cd /usr/local && ln -s hadoop-$HADOOP_VER hadoop

#Deploy Thrift Server
ADD thrift-server /thrift-server

RUN tar -xvzf /thrift-server/thrift-server-$THRIFT_SERVER_VERSION.tar.gz -C /usr/local

RUN ln -s /usr/local/thrift-server-$THRIFT_SERVER_VERSION /usr/local/thrift-server

#Download jst2geojson
RUN wget -P /usr/local/spark/jars/ "https://repo1.maven.org/maven2/org/wololo/jts2geojson/0.16.1/jts2geojson-0.16.1.jar"

RUN chown -R root:root /usr/local/thrift-server
RUN chmod -R 744 /usr/local/thrift-server

#Set Environment Variable
ENV JAVA_HOME /usr/lib/jvm/jre-1.8.0-openjdk
ENV THRIFT_HOME /usr/local/thrift-server
ENV GEOHIKER_HOME /usr/local/thrift-server
ENV SPARK_HOME /usr/local/spark
ENV PYTHONPATH $SPARK_HOME/python/:$PYTHONPATH$

ENV HIVE_HOME /usr/local/hive
ENV HIVE_CONF_DIR /usr/local/hive/conf

ENV HADOOP_PREFIX /usr/local/hadoop
ENV HADOOP_HOME /usr/local/hadoop
ENV HADOOP_MAPRED_HOME ${HADOOP_HOME}
ENV HADOOP_COMMON_HOME ${HADOOP_HOME}
ENV HADOOP_HDFS_HOME ${HADOOP_HOME}
ENV YARN_HOME ${HADOOP_HOME}

ENV HADOOP_COMMON_LIB_NATIVE_DIR ${HADOOP_HOME}/lib/native
ENV HADOOP_OPT "-Djava.library.path=$HADOOP_PREFIX/lib/native"

ENV PATH $PATH:$JAVA_HOME/bin
ENV PATH $SPARK_HOME/bin:$PATH
ENV PATH $HIVE_HOME/bin:$PATH
ENV PATH $HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH

RUN wget -P /usr/local/spark/jars/ https://jdbc.postgresql.org/download/postgresql-42.2.19.jar --no-check-certificate

RUN yum -y update
RUN yum install -y epel-release 
RUN yum -y install supervisor
RUN yum -y install dos2unix

WORKDIR /
ADD run-thrift.sh run-thrift.sh
RUN chmod a+x run-thrift.sh

ADD supervisord.conf supervisord.conf
ADD ivy.settings ivy.settings
ADD thrift-server.sh /usr/local/thrift-server/bin/thrift-server.sh 

RUN dos2unix /usr/local/thrift-server/bin/thrift-server.sh
RUN dos2unix /usr/local/thrift-server/sbin/datacore-start-thriftserver.sh
RUN dos2unix /usr/local/thrift-server/sbin/datacore-stop-thriftserver.sh

RUN chmod +x /usr/local/thrift-server/bin/thrift-server.sh
RUN chmod +x /usr/local/thrift-server/sbin/datacore-start-thriftserver.sh
RUN chmod +x /usr/local/thrift-server/sbin/datacore-stop-thriftserver.sh

ENTRYPOINT ["supervisord", "-c", "/supervisord.conf"]