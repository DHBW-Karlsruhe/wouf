/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keyCrawler;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.zip.*;


/**
 *
 * @author Marco Hammel
 */
public class ClassCrawler {

   public static void main( String[] args ) throws Exception
   {
      String packageName       = ( args.length > 0 ) ? args[0] : null;
      String classNameSearched = ( args.length > 1 ) ? args[1] : null;
      System.out.println( "\n---- Gefundene Klassen in " + packageName + ":" );
      List<Class<?>> classes = getClasses( packageName, classNameSearched );
      for( Class<?> clazz : classes )
         System.out.println( clazz );
      System.out.println( "\n---- Instanziierte Objekte:" );
      List<Object> objects = getInstances( packageName, classNameSearched );
      for( Object obj : objects )
         System.out.println( obj.getClass() );
   }

   // Finde Klassen und instanziiere sie:
   public static List<Object> getInstances( String packageName, String classNameSearched ) throws ClassNotFoundException
   {
      List<Class<?>> classes = ClassCrawler.getClassesSimple( packageName, classNameSearched );
      List<Object>   objects = new ArrayList<Object>();
      for( Class<?> clazz : classes ) {
         if( !clazz.isInterface() && (clazz.getModifiers() & Modifier.ABSTRACT) == 0 ) {
            try {
               objects.add( clazz.newInstance() );
            } catch( Exception ex ) {
               // nur instanziierbare Klassen sind interessant
            }
         }
      }
      return objects;
   }

//   // Finde Klassen (über Interface- oder Klassennamen bzw. Package-Namen):
   public static List<Class<?>> getClassesSimple( String packageName, String classNameSearched ) throws ClassNotFoundException
   {
      Class<?> classSearched = ( classNameSearched != null ) ? Class.forName( classNameSearched ) : null;
      return getClasses( packageName, classSearched );
   }

   // Finde Klassen (über Interface oder Klasse bzw. Package-Namen):
   public static List<Class<?>> getClasses( String packageName, Class<?> classSearched )
   {
      List<Class<?>> classes = new ArrayList<Class<?>>();
      for( String path : getPathesFromClasspath() ) {
         File fileOrDir = new File( path );
         if( fileOrDir.isDirectory() )
            classes.addAll( getClassesFromDir( fileOrDir, packageName, classSearched ) );
         if( fileOrDir.isFile() && (fileOrDir.getName().toLowerCase().endsWith( ".jar" ) ||
                                    fileOrDir.getName().toLowerCase().endsWith( ".zip" )) )
            classes.addAll( getClassesFromJar( fileOrDir, packageName, classSearched ) );
      }
      return Collections.unmodifiableList( classes );
   }

   public static List<String> getPathesFromClasspath()
   {
      String          classpath     = System.getProperty( "java.class.path" );
      String          pathseparator = System.getProperty( "path.separator" );
      StringTokenizer tokenizer     = new StringTokenizer( classpath, pathseparator );
      List<String>    pathes        = new ArrayList<String>();
      while( tokenizer.hasMoreElements() )
         pathes.add(tokenizer.nextToken());
      return Collections.unmodifiableList( pathes );
   }

   public static List<Class<?>> getClassesFromJar( File file, String packageName, Class<?> classSearched )
   {
      if( packageName == null ) packageName = "";
      List<Class<?>> classes = new ArrayList<Class<?>>();
      String         dirSearched = packageName.replace( ".", "/" );
      ZipFile        zipFile = null;
      try {
         zipFile = new ZipFile( file );
      } catch( Exception ex ) {
         // nur Dateien, die gezippt sind und geöffnet werden können, sind interessant
         return classes;
      }
      for( Enumeration<? extends ZipEntry> zipEntries = zipFile.entries(); zipEntries.hasMoreElements(); )
      {
         String entryName = zipEntries.nextElement().getName();
         if( !entryName.startsWith( dirSearched ) ||
             !entryName.toLowerCase().endsWith( ".class" ) )
            continue;
         entryName = entryName.substring( 0, entryName.length() - ".class".length() );
         entryName = entryName.replace( "/","." ) ;
         try {
            Class<?> clazz = Class.forName( entryName );
            if( classSearched == null || classSearched.isAssignableFrom( clazz ) )
               classes.add( clazz );
         } catch( Throwable ex ) {
            // nur 'verwendbare' Klassen sind interessant
         }
      }
      return Collections.unmodifiableList( classes );
   }

   public static List<Class<?>> getClassesFromDir( File dir, String packageName, Class<?> classSearched )
   {
      if( packageName == null ) packageName = "";
      List<Class<?>> classes = new ArrayList<Class<?>>();
      File dirSearched = new File( dir.getPath() + File.separator + packageName.replace( ".", "/" ) );
      if( dirSearched.isDirectory() )
         getClassesFromFileOrDirIntern( true, dirSearched, packageName, classSearched, classes );
      return Collections.unmodifiableList( classes );
   }

   private static void getClassesFromFileOrDirIntern( boolean first, File fileOrDir, String packageName,
                                                      Class<?> classSearched, List<Class<?>> classes )
   {
      if( fileOrDir.isDirectory() )
      {
         if( !first )
            packageName = (packageName + "." + fileOrDir.getName()).replaceAll( "^\\.", "" );
         for( String subFileOrDir : fileOrDir.list() )
            getClassesFromFileOrDirIntern( false, new File( fileOrDir, subFileOrDir ),
                                           packageName, classSearched, classes );
      }
      else
      {
         if( fileOrDir.getName().toLowerCase().endsWith( ".class" ) )
         {
            String classFile = fileOrDir.getName();
            classFile = packageName + "." + classFile.substring( 0, classFile.length() - ".class".length() );
            try {
               Class<?> clazz = Class.forName( classFile );
               if( classSearched == null || classSearched.isAssignableFrom( clazz ) )
                  classes.add( clazz );
            } catch( Throwable ex ) {
               // nur 'verwendbare' Klassen sind interessant
            }
         }
      }
   }

}
