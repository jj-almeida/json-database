package server.database;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private static final Database INSTANCE = new Database();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final File database;
    private final String fileName = System.getProperty("user.dir") + File.separator +
            "JSON Database" + File.separator +
            "task" + File.separator +
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator +
            "db.json";

    private Database() {
        this.database = new File(this.fileName);
        writeDatabase(new JsonObject());
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public void set(JsonElement key, JsonElement value) {
        JsonObject json = readDatabase();
        JsonElement tmp = json;

        if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();

            for (int i = 0; i < keys.size() - 1; i++) {
                if (!tmp.getAsJsonObject().has(keys.get(i).getAsString())) {
                    tmp.getAsJsonObject().add(keys.get(i).getAsString(), new JsonObject());
                }
                tmp = tmp.getAsJsonObject().get(keys.get(i).getAsString());
            }
            String toAdd = keys.remove(keys.size() - 1).getAsString();
            tmp.getAsJsonObject().add(toAdd, value);
        } else {
            json.add(key.getAsString(), value);
        }
        writeDatabase(json);
    }

    public JsonElement get(JsonElement key) {
        if (key.isJsonArray()) {
            return getFromArray(key.getAsJsonArray());
        } else {
            return readDatabase().getAsJsonObject().get(key.getAsString());
        }
    }

    public String delete(JsonElement key) {
        JsonObject json = readDatabase();
        JsonElement tmp = json;
        JsonElement result;

        if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();

            for (int i = 0; i < keys.size() - 1; i++) {
                if (!tmp.getAsJsonObject().has(keys.get(i).getAsString())) {
                    return null;
                }
                tmp = tmp.getAsJsonObject().get(keys.get(i).getAsString());
            }
            String toRemove = keys.remove(keys.size() - 1).getAsString();
            result = tmp.getAsJsonObject().remove(toRemove);
        } else {
            result = json.remove(key.getAsString());
        }

        writeDatabase(json);
        return result.getAsString();
    }

    private JsonObject readDatabase() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            String json = new String(Files.readAllBytes(Paths.get(fileName)));
            return new Gson().fromJson(json, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + e.getMessage());
        } finally {
            readLock.unlock();
        }
    }

    private void writeDatabase(JsonObject jsonObject) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try (FileWriter writer = new FileWriter(this.database)) {
            writer.write(new Gson().toJson(jsonObject));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }

    private JsonElement getFromArray(JsonArray keys) {
        JsonElement json = readDatabase();
        for (JsonElement key : keys) {
            if (json == null)
                return null;
            json = json.getAsJsonObject().get(key.getAsString());
        }
        return json;
    }
}
