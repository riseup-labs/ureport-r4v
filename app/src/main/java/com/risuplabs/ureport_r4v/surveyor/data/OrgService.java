package com.risuplabs.ureport_r4v.surveyor.data;

import com.risuplabs.ureport_r4v.surveyor.net.TembaException;
import com.risuplabs.ureport_r4v.utils.surveyor.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Directory based service for org configurations
 */
public class OrgService {

    private final File rootDir;

    private final Map<String, Org> cache = new HashMap<>();

    public OrgService(File rootDir) {
        this.rootDir = rootDir;

        Logger.d("OrgService created for directory " + this.rootDir.getAbsolutePath());
    }

    public Org get(String uuid) throws IOException {
        if (cache.containsKey(uuid)) {
            Logger.d("Returning cached org " + uuid);
            return cache.get(uuid);
        }

        File directory = new File(rootDir, uuid);
        Org org = Org.load(directory);
        Logger.d("Loaded org " + uuid);
//        cache.put(uuid, org);
        return org;
    }

    /**
     * Fetches an org using the given API token and saves it to the org storage
     *
     * @param uuid  the UUID of the org
     * @param name  the name of the org
     * @param token the API token
     */
    public Org getOrFetch(String uuid, String name, String token) throws TembaException, IOException {
        File directory = new File(rootDir, uuid);
        if (directory.exists() && directory.isDirectory()) {
            return get(uuid);
        }

        Org org = Org.create(directory, name, token);
        org.refresh(false, null);
        return org;
    }

    public void clearCache() {
        cache.clear();
    }
}
