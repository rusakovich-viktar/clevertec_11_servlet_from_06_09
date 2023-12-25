package by.clevertec.servlet;

import static by.clevertec.util.Constants.Messages.DATATABLE_NOT_CREATED;

import by.clevertec.service.DatabaseMigrationService;
import by.clevertec.util.Constants.Attributes;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/db")
public class InitDataBaseServlet extends HttpServlet {

    private DatabaseMigrationService migrationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        migrationService = (DatabaseMigrationService) config.getServletContext().getAttribute(Attributes.MIGRATION_SERVICE);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean success = migrationService.migrateDatabase();
        if (success) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, DATATABLE_NOT_CREATED);
        }
    }
}
