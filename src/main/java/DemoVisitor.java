import org.eclipse.jdt.core.dom.*;

import java.util.List;


public class DemoVisitor extends ASTVisitor {

//    @Override
//    public boolean visit(FieldDeclaration node) {
//        for (Object obj: node.fragments()) {
//            VariableDeclarationFragment v = (VariableDeclarationFragment)obj;
//            System.out.println("Field:\t" + v.getName());
//        }
//
//        return true;
//    }
    static String jdkPrefix[] = {"java.applet", "java.awt", "java.awt.color", "java.awt.datatransfer", "java.awt.dnd",
        "java.awt.event", "java.awt.font", "java.awt.geom", "java.awt.im", "java.awt.image", "java.awt.image.renderable", "java.awt.im.spi", "java.awt.print", "java.beans", "java.beans.beancontext", "java.io", "java.lang", "java.lang.annotation", "java.lang.instrument", "java.lang.invoke", "java.lang.management", "java.lang.ref", "java.lang.reflect", "java.math", "java.net", "java.nio", "java.nio.channels", "java.nio.channels.spi", "java.nio.charset", "java.nio.charset.spi", "java.nio.file", "java.nio.file.attribute", "java.nio.file.spi", "java.rmi", "java.rmi.activation", "java.rmi.dgc", "java.rmi.registry", "java.rmi.server", "java.security", "java.security.acl", "java.security.cert", "java.security.interfaces", "java.security.spec", "java.sql", "java.text", "java.text.spi", "java.util", "java.util.concurrent", "java.util.concurrent.atomic", "java.util.concurrent.locks", "java.util.jar", "java.util.logging", "java.util.prefs", "java.util.regex", "java.util.spi", "java.util.zip", "javax.accessibility", "javax.activation", "javax.activity", "javax.annotation", "javax.annotation.processing", "javax.crypto", "javax.crypto.interfaces", "javax.crypto.spec", "javax.imageio", "javax.imageio.event", "javax.imageio.metadata", "javax.imageio.plugins.bmp", "javax.imageio.plugins.jpeg", "javax.imageio.spi", "javax.imageio.stream", "javax.jws", "javax.jws.soap", "javax.lang.model", "javax.lang.model.element", "javax.lang.model.type", "javax.lang.model.util", "javax.management", "javax.management.loading", "javax.management.modelmbean", "javax.management.monitor", "javax.management.openmbean", "javax.management.relation", "javax.management.remote", "javax.management.remote.rmi", "javax.management.timer", "javax.naming", "javax.naming.directory", "javax.naming.event", "javax.naming.ldap", "javax.naming.spi", "javax.net", "javax.net.ssl", "javax.print", "javax.print.attribute", "javax.print.attribute.standard", "javax.print.event", "javax.rmi", "javax.rmi.CORBA", "javax.rmi.ssl", "javax.script", "javax.security.auth", "javax.security.auth.callback", "javax.security.auth.kerberos", "javax.security.auth.login", "javax.security.auth.spi", "javax.security.auth.x500", "javax.security.cert", "javax.security.sasl", "javax.sound.midi", "javax.sound.midi.spi", "javax.sound.sampled", "javax.sound.sampled.spi", "javax.sql", "javax.sql.rowset", "javax.sql.rowset.serial", "javax.sql.rowset.spi", "javax.swing", "javax.swing.border", "javax.swing.colorchooser", "javax.swing.event", "javax.swing.filechooser", "javax.swing.plaf", "javax.swing.plaf.basic", "javax.swing.plaf.metal", "javax.swing.plaf.multi", "javax.swing.plaf.nimbus", "javax.swing.plaf.synth", "javax.swing.table", "javax.swing.text", "javax.swing.text.html", "javax.swing.text.html.parser", "javax.swing.text.rtf", "javax.swing.tree", "javax.swing.undo", "javax.tools", "javax.transaction", "javax.transaction.xa", "javax.xml", "javax.xml.bind", "javax.xml.bind.annotation", "javax.xml.bind.annotation.adapters", "javax.xml.bind.attachment", "javax.xml.bind.helpers", "javax.xml.bind.util", "javax.xml.crypto", "javax.xml.crypto.dom", "javax.xml.crypto.dsig", "javax.xml.crypto.dsig.dom", "javax.xml.crypto.dsig.keyinfo", "javax.xml.crypto.dsig.spec", "javax.xml.datatype", "javax.xml.namespace", "javax.xml.parsers", "javax.xml.soap", "javax.xml.stream", "javax.xml.stream.events", "javax.xml.stream.util", "javax.xml.transform", "javax.xml.transform.dom", "javax.xml.transform.sax", "javax.xml.transform.stax", "javax.xml.transform.stream", "javax.xml.validation", "javax.xml.ws", "javax.xml.ws.handler", "javax.xml.ws.handler.soap", "javax.xml.ws.http", "javax.xml.ws.soap", "javax.xml.ws.spi", "javax.xml.ws.spi.http", "javax.xml.ws.wsaddressing", "javax.xml.xpath", "org.ietf.jgss", "org.omg.CORBA", "org.omg.CORBA_2_3", "org.omg.CORBA_2_3.portable", "org.omg.CORBA.DynAnyPackage", "org.omg.CORBA.ORBPackage", "org.omg.CORBA.portable", "org.omg.CORBA.TypeCodePackage", "org.omg.CosNaming", "org.omg.CosNaming.NamingContextExtPackage", "org.omg.CosNaming.NamingContextPackage", "org.omg.Dynamic", "org.omg.DynamicAny", "org.omg.DynamicAny.DynAnyFactoryPackage", "org.omg.DynamicAny.DynAnyPackage", "org.omg.IOP", "org.omg.IOP.CodecFactoryPackage", "org.omg.IOP.CodecPackage", "org.omg.Messaging", "org.omg.PortableInterceptor", "org.omg.PortableInterceptor.ORBInitInfoPackage", "org.omg.PortableServer", "org.omg.PortableServer.CurrentPackage", "org.omg.PortableServer.POAManagerPackage", "org.omg.PortableServer.POAPackage", "org.omg.PortableServer.portable", "org.omg.PortableServer.ServantLocatorPackage", "org.omg.SendingContext", "org.omg.stub.java.rmi", "org.w3c.dom", "org.w3c.dom.bootstrap", "org.w3c.dom.events", "org.w3c.dom.ls", "org.xml.sax", "org.xml.sax.ext", "org.xml.sax.helpers"};
    public static boolean isJdkApi(String s) {
        for(String si:jdkPrefix){
            if(s.startsWith(si)) return true;
        }
        return false;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        System.out.println("Method:\t" + node.getName());
        return true;
    }

//    @Override
//    public boolean visit(TypeDeclaration node) {
//        System.out.println("Class:\t" + node.getName());
//        return true;
//    }

    @Override
    public boolean visit(Javadoc node) {
        List tags = node.tags();
        System.out.println("Javadoc:\t" + tags);
        if(tags.size() > 0) {
            String fullDocs = tags.get(0).toString().trim();
            String[] sentences = fullDocs.split("\\. ");
            for(String sentence: sentences) {
                if(sentence.trim().length() > 0) {
                    System.out.println(sentence.trim());
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        System.out.println("MethodInvocation:\t" + node.toString());
        System.out.println("\tExpression: " + node.getExpression());
        Expression expression = node.getExpression();
        if(expression != null) {
            ITypeBinding  typeBinding = expression.resolveTypeBinding();
            if (typeBinding != null) {
                System.out.println("\tType: " + typeBinding.getName());
                String qualifiedName = typeBinding.getQualifiedName();
                System.out.println("\tis from source : \t" + qualifiedName);
                System.out.println("\tIsFromJDK: " + isJdkApi(qualifiedName));
            }
        }

        System.out.println("\tName:" + node.getName());
        System.out.println("\t" + node.resolveMethodBinding());
//        System.out.println("\t" + node.arguments());
//        System.out.println("\t" + node.typeArguments());
//        System.out.println("\t" + node.resolveTypeBinding());
        return true;
    }


    public void endVisit(ClassInstanceCreation node) {
        System.out.println("ClassInstanceCreation:\t" + node.toString());
        Expression expression = node.getExpression();
//        if(expression != null){
//            System.out.println(expression.resolveTypeBinding().getQualifiedName());
//        }
//        else {
//            System.out.println("\tnull found!");
//        }
//        System.out.println("\tType: " + node.getType().toString());
        String qualifiedName = node.getType().resolveBinding().getQualifiedName();
        System.out.println("\tQualifiedName: " + qualifiedName );
        System.out.println("\tIsFromJDK: " + isJdkApi(qualifiedName));


//        return true;
    }
}