package bg.tu_varna.sit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private String dirName = "homework_1";

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/homework/homework_10/test-interface.csv", numLinesToSkip = 1)
    public void testExistingInterfaceTest(String packages, String clazzName, @ConvertWith(StringArrayConverter.class) String[] methodNames, @ConvertWith(StringArrayConverter.class) String[] type) throws ClassNotFoundException, NoSuchMethodException, SecurityException
    {
        Class<?> clazz = Class.forName(packages + "." + clazzName);            
        assertEquals(clazzName, clazz.getSimpleName());
        assertTrue(Modifier.isInterface(clazz.getModifiers()));

        // for (int i = 0; i < methodNames.length; i ++) {
        //     Method method = clazz.getDeclaredMethod(methodNames[i]);
        //     assertNotNull(method);
        //     assertEquals(type[i].toLowerCase(), method.getReturnType().getName().toLowerCase());
        // }
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/homework/homework_10/test-class.csv", numLinesToSkip = 1)
    public void testExistingClassTest(String packages, String clazzName, String type) throws ClassNotFoundException
    {
        Class<?> clazz = Class.forName(packages + "." + clazzName);
        assertModifier(type, clazz.getModifiers());
        assertEquals(clazzName, clazz.getSimpleName());
    }

   @ParameterizedTest
   @CsvFileSource(files = "src/test/resources/homework/homework_10/test-method.csv", numLinesToSkip = 1)
   public void testExistingMethodInClassTest(String packages, String clazzName, String methodName, String type, String value, @ConvertWith(StringArrayConverter.class) String[] params) throws ClassNotFoundException, NoSuchMethodException, SecurityException
   {
        Class<?> animalClass = Class.forName(packages + "." + clazzName);
        
        Method method = null;

        if (params != null && params.length > 0) {
            List<Class<?>> clazz = new ArrayList<>(params.length);
            for(String item : params) {
                clazz.add(parseType(item));
            }
            method = animalClass.getDeclaredMethod(methodName, clazz.toArray(new Class<?>[0]));
        } else {
            method = animalClass.getDeclaredMethod(methodName);
        }

        assertNotNull(method);
        assertEquals(value.toLowerCase(), method.getReturnType().getName().toLowerCase());
        if(type.toLowerCase().equals("abstract")) {
            assertTrue(Modifier.isAbstract(method.getModifiers()) && !Modifier.isInterface(method.getModifiers()));
        }
   }

   @ParameterizedTest
   @CsvFileSource(files = "src/test/resources/homework/homework_10/test-field.csv", numLinesToSkip = 1)
    public void testExistingFildInClassTest(String packages, String clazzName, String fieldName, String type, String modificator) throws ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException
    {
        Class<?> animalClass = Class.forName(packages + "." + clazzName);
        Field field = animalClass.getDeclaredField(fieldName);
        assertNotNull(field);
        assertModifier(modificator, field.getModifiers());
        assertEquals(type.toLowerCase(), field.getType().getName().toLowerCase());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/homework/homework_10/test-extends.csv", numLinesToSkip = 1)
    public void testExistingParentClassTest(String packages, String clazzName, String parentName, String modificator) throws ClassNotFoundException, NoSuchMethodException, SecurityException
    {
        Class<?> animalClass = Class.forName(packages + "." + clazzName);
        if(modificator.equals("implements")) {
            Class<?>[] goatSuperClass = animalClass.getInterfaces();
            if(goatSuperClass.length > 1) {
                assertTrue((goatSuperClass[0].getSimpleName() + goatSuperClass[1].getSimpleName()).contains(parentName));
            } else {
                assertEquals(parentName, goatSuperClass[0].getSimpleName());
            }
        } else {
            Class<?> goatSuperClass = animalClass.getSuperclass();
            assertEquals(parentName, goatSuperClass.getSimpleName());
        }
    }

    private void assertModifier(String modificator, int modifiers) {

        switch (modificator) {
            case "protected":
                assertTrue(Modifier.isProtected(modifiers));
            break;
            case "private":
                assertTrue(Modifier.isPrivate(modifiers));
            break;
            case "public":
                assertTrue(Modifier.isPublic(modifiers));
            break;
            case "abstract":
            assertTrue(Modifier.isAbstract(modifiers) && !Modifier.isInterface(modifiers));
            break;
            default:
                assertTrue(false);
            break;
        }
    }

    public Class<?> parseType(final String className) throws ClassNotFoundException {
        switch (className) {
            case "boolean":
                return boolean.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            case "void":
                return void.class;
            default:
                return Class.forName(className);
                //String fqn = className.contains(".") ? className : "java.lang.".concat(className);
                //try {
                //    return Class.forName(fqn);
                //} catch (ClassNotFoundException ex) {
                //    throw new IllegalArgumentException("Class not found: " + fqn);
                //}
        }
    }
}
