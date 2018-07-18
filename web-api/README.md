使用启动jar包的方式启动

（1）首先进入项目所在目录，如果是mac系统在项目上右键，选择Reveal in Finder,Windows系统在项目上右键选择Show in Explorer，即可打开项目所在目录

（2）打开终端，进入项目所在目录

     cd /Users/shanml/IdeaProjects/SpringbootDemo

     输入mvn install,构建项目

（3）构建成功后，在项目target文件夹下会多出一个jar包

（4）使用java -jar springbootdemo-0.0.1-SNAPSHOT.jar

     启动jar包即可