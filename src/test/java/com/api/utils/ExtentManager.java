package com.api.utils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            extent = createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        cleanupOldReports(); // ✅ Auto cleanup + zipping before creating a new report

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportName = "ExtentReport_" + timestamp + ".html";
        String reportsDir = System.getProperty("user.dir") + "/Reports/";
        String filePath = reportsDir + reportName;

        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setDocumentTitle("Campus Automation API Report");
        reporter.config().setReportName("API Automation Execution Report");
        reporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("Project", "CampusAutomationAPI");
        extent.setSystemInfo("Tester", "Naveen QA");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tool", "Rest Assured + TestNG");

        return extent;
    }

    // ✅ Auto cleanup logic: keeps last 5 reports, archives & zips old ones
    private static void cleanupOldReports() {
        String reportsDirPath = System.getProperty("user.dir") + "/Reports";
        String archiveDirPath = System.getProperty("user.dir") + "/ArchiveReports";

        File reportsDir = new File(reportsDirPath);
        File archiveDir = new File(archiveDirPath);
        if (!archiveDir.exists()) archiveDir.mkdir();

        File[] reportFiles = reportsDir.listFiles((dir, name) -> name.endsWith(".html"));
        if (reportFiles == null || reportFiles.length <= 5) return;

        Arrays.sort(reportFiles, Comparator.comparingLong(File::lastModified).reversed());

        List<File> toArchive = new ArrayList<>();
        for (int i = 5; i < reportFiles.length; i++) {
            toArchive.add(reportFiles[i]);
        }

        if (!toArchive.isEmpty()) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String zipFileName = archiveDirPath + "/Reports_" + date + ".zip";
            zipFiles(toArchive, zipFileName);

            // After zipping, delete moved files
            for (File f : toArchive) {
                try {
                    Files.deleteIfExists(f.toPath());
                } catch (IOException e) {
                    System.out.println("⚠️ Could not delete old report: " + f.getName());
                }
            }
        }
    }

    // ✅ Utility method to zip multiple files
    private static void zipFiles(List<File> files, String zipFilePath) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                }
            }

            System.out.println("✅ Archived & zipped old reports into: " + zipFilePath);

        } catch (IOException e) {
            System.out.println("⚠️ Error while zipping reports: " + e.getMessage());
        }
    }
}