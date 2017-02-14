package com.letv.annotion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({"com.letv.annotion.LetvClassAutoLoad"})
public class LetvPluginAnnotationProcessor extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = this.processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, "实现@LetvClassAutoLoad注解的class 数量: " + annotations.size());
        if (annotations.size() > 0) {
            String path = "./app/src/main/assets/";
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            try {
                File staticJsonFile = new File(path + "static.txt");
                if (!staticJsonFile.exists()) {
                    staticJsonFile.createNewFile();
                }
                InputStreamReader inputReader = new InputStreamReader(new FileInputStream(staticJsonFile));
                BufferedReader bufReader = new BufferedReader(inputReader);
                Set<String> resultSet = new HashSet();
                while (true) {
                    String line = bufReader.readLine();
                    if (line == null) {
                        break;
                    }
                    resultSet.add(line);
                }
                bufReader.close();
                inputReader.close();
                boolean hasDif = false;
                for (TypeElement te : annotations) {
                    for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                        String className = e.toString();
                        if (!resultSet.contains(className)) {
                            resultSet.add(className);
                            hasDif = true;
                        }
                    }
                }
                if (hasDif) {
                    messager.printMessage(Kind.NOTE, "更新class映射文件 static.txt");
                    BufferedWriter outPut = new BufferedWriter(new FileWriter(staticJsonFile));
                    for (String str : resultSet) {
                        outPut.write(str + "\n");
                        messager.printMessage(Kind.NOTE, "写入中: " + str);
                    }
                    outPut.close();
                    messager.printMessage(Kind.NOTE, "更新class映射文件 static.txt 成功");
                } else {
                    messager.printMessage(Kind.NOTE, "不存在未包含 class name");
                    return true;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                messager.printMessage(Kind.NOTE, "更新class映射文件static.txt 失败 :  /app/src/main/assets/ 路径无效,请检查路径");
            }
        }
        return true;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
