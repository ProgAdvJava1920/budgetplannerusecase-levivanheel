package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");

    public void importCV(Path pad) {
        if (!csvMatcher.matches(pad)) {
            LOGGER.error("Invalid file: .csv expected. Provided {}",pad);
        }

        if (!Files.exists(pad)) {
            LOGGER.error("File {} does not exist",pad);
        }

        try (BufferedReader reader = Files.newBufferedReader(pad)) {
            String line = null;
            boolean skip = false;
            while ((line = reader.readLine()) != null) {
                if (skip == false) {
                    skip = true;
                }
                else {
                    AccountMapper accountMapper = new AccountMapper();
                    Account account = accountMapper.map(line);
                    System.out.println(account.toString());
                }
            }
        } catch (IOException ex) {
            LOGGER.fatal("An error occured while reading : {}", pad);
        }
    }
}
