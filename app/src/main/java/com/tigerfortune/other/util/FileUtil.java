package com.tigerfortune.other.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static void clearFile(Context context, String fileName) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            // Просто не пишем ничего — это перезапишет файл пустотой
            Log.d("FileUtils", "Файл очищен: " + fileName);
        } catch (IOException e) {
            Log.e("FileUtils", "Ошибка при очистке файла: " + fileName, e);
        }
    }

    public static void deleteFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Log.d("FileUtils", "Файл успешно удалён: " + fileName);
            } else {
                Log.e("FileUtils", "Не удалось удалить файл: " + fileName);
            }
        } else {
            Log.w("FileUtils", "Файл не найден: " + fileName);
        }
    }

}
