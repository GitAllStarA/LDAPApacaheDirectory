# LDAPApacaheDirectory
CRUD operations

This Maven Project Helps in manipulate data on the ApacheDS 
Requiremnts 
Java 11 or lower as of date 2 May 2022
Apache DS

configuration of ApacaheDS at applicatin level /Applications/ApacheDirectoryStudio.app/Contents/Eclipse/ApacheDirectoryStudio.ini

#Uncomment_to_configure_Java_version_to_use
#https://directory.apache.org/studio/faqs.html#how-to-set-the-java-vm-to-use
-vm
#/usr/lib/jvm/java-11-openjdk/bin/java
/Library/Java/JavaVirtualMachines/jdk-11.0.15.jdk/Contents/Home
-startup
-vmargs
 -Dosgi.requiredJavaVersion=11
../Eclipse/plugins/org.eclipse.equinox.launcher_1.6.0.v20200915-1508.jar
--launcher.library
../Eclipse/plugins/org.eclipse.equinox.launcher.cocoa.macosx.x86_64_1.2.0.v20200915-1442
/studio-rcp/resources/icons/linux/studio.xpm

###
#Uncomment_to_configure_the_language
#https://directory.apache.org/studio/faqs.html#how-to-set-the-language-of-studio
#-nl
#en

###
#Uncomment_to_configure_heap_memory
#https://directory.apache.org/studio/faqs.html#how-to-increase-the-heap-memory
#-Xms1g
#-Xmx2g
-XstartOnFirstThread
-Dorg.eclipse.swt.internal.carbon.smallFonts


OS: Setup version 11 Java Path in Windows, JAVA_HOME in linux or MacOS




With help of this code we can perform below operations : 
  1. Create users , 
  2. Update certain user attributes, 
  3. Delete users, 
  4. Add to users to group,
