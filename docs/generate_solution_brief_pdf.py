#!/usr/bin/env python3
"""Generate the shareable Spaza Stock & Sales Tracker solution brief."""

from pathlib import Path
import textwrap


OUTPUT = Path(__file__).with_name(
    "Romeo_Mduduzi_Spaza_Stock.pdf"
)
PAGE_WIDTH = 612
PAGE_HEIGHT = 792
MARGIN = 50
TEAL = (0.06, 0.39, 0.37)
INK = (0.12, 0.17, 0.20)
MUTED = (0.34, 0.40, 0.43)
PALE = (0.91, 0.97, 0.96)
LINE = (0.82, 0.87, 0.87)
WHITE = (1, 1, 1)


def pdf_text(value):
    encoded = value.encode("ascii", "replace").decode("ascii")
    return encoded.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)")


class Page:
    def __init__(self, number):
        self.number = number
        self.commands = []
        self.links = []
        self.header()

    def fill_color(self, color):
        self.commands.append(f"{color[0]:.3f} {color[1]:.3f} {color[2]:.3f} rg")

    def stroke_color(self, color):
        self.commands.append(f"{color[0]:.3f} {color[1]:.3f} {color[2]:.3f} RG")

    def rect(self, x, y, width, height, color):
        self.fill_color(color)
        self.commands.append(f"{x} {y} {width} {height} re f")

    def rule(self, x1, y1, x2, y2, color=LINE, width=0.8):
        self.stroke_color(color)
        self.commands.extend(
            [f"{width} w", f"{x1} {y1} m {x2} {y2} l S"]
        )

    def text(self, x, y, value, size=10, font="F1", color=INK):
        self.fill_color(color)
        self.commands.append(
            f"BT /{font} {size} Tf 1 0 0 1 {x} {y} Tm ({pdf_text(value)}) Tj ET"
        )

    def hyperlink(self, x, y, url, size=8.6, leading=11):
        self.text(x, y, url, size, "F1", TEAL)
        width = min(len(url) * size * 0.52, PAGE_WIDTH - MARGIN - x)
        self.links.append((x, y - 2, x + width, y + size + 2, url))
        return y - leading

    def header(self):
        self.text(MARGIN, 766, "SPAZA STOCK & SALES TRACKER", 8, "F2", TEAL)
        self.text(427, 766, "SOLUTION BRIEF", 8, "F2", MUTED)
        self.rule(MARGIN, 754, PAGE_WIDTH - MARGIN, 754)

    def footer(self):
        self.rule(MARGIN, 42, PAGE_WIDTH - MARGIN, 42)
        self.text(MARGIN, 26, "Samsung Innovation Internship 2027 | Route A", 8, "F1", MUTED)
        self.text(526, 26, str(self.number), 8, "F2", MUTED)

    def section(self, y, number, title):
        self.text(MARGIN, y, f"{number}  {title}", 14, "F2", TEAL)
        self.rule(MARGIN, y - 8, PAGE_WIDTH - MARGIN, y - 8, LINE, 0.6)
        return y - 25

    def paragraph(self, x, y, value, width=510, size=10, leading=14, color=INK):
        char_width = size * 0.52
        line_width = max(25, int(width / char_width))
        lines = textwrap.wrap(value, width=line_width, break_long_words=False)
        for line in lines:
            self.text(x, y, line, size, "F1", color)
            y -= leading
        return y - 7

    def bullet(self, y, value, width=494, size=9.6, leading=13):
        self.fill_color(TEAL)
        self.commands.append(f"{MARGIN + 4} {y + 2} 4 4 re f")
        x = MARGIN + 17
        char_width = size * 0.52
        line_width = max(25, int(width / char_width))
        lines = textwrap.wrap(value, width=line_width, break_long_words=False)
        for line in lines:
            self.text(x, y, line, size, "F1", INK)
            y -= leading
        return y - 5


def build_pages():
    first = Page(1)
    first.rect(MARGIN, 606, PAGE_WIDTH - 2 * MARGIN, 128, PALE)
    first.rect(MARGIN, 606, 5, 128, TEAL)
    first.text(MARGIN + 20, 711, "SAMSUNG INNOVATION INTERNSHIP 2027  |  ROUTE A", 8, "F2", TEAL)
    first.text(MARGIN + 20, 678, "Spaza Stock & Sales Tracker", 23, "F2", INK)
    first.text(MARGIN + 20, 651, "A practical inventory and point-of-sale platform for small retailers.", 11, "F1", MUTED)
    first.text(
        MARGIN + 20,
        625,
        "Prepared by Romeo Mduduzi Mangena  |  13 November 2026",
        9,
        "F1",
        MUTED,
    )

    y = first.section(580, "01", "Executive summary")
    y = first.paragraph(
        MARGIN,
        y,
        "The Spaza Stock & Sales Tracker brings product records, counter sales, "
        "stock visibility, and restocking decisions into one lightweight web "
        "application. Owners and staff can find products, record transactions, "
        "and review shop activity from a single interface.",
    )
    y = first.paragraph(
        MARGIN,
        y,
        "This is a working full-stack prototype designed around small-retailer "
        "workflows. Business impact is an intended outcome, not a measured result.",
    )

    y = first.section(y - 2, "02", "The operational need")
    y = first.paragraph(
        MARGIN,
        y,
        "Manual stock and sales records can make it difficult to keep quantities "
        "current, notice low-stock items early, and estimate what to reorder. "
        "The solution supports consistent sale recording and a clearer view of "
        "inventory without requiring complex retail systems.",
    )

    y = first.section(y - 2, "03", "Core workflows")
    y = first.bullet(
        y,
        "Product catalogue: maintain names, categories, prices, quantities, "
        "suppliers, and reorder thresholds; search and filter the catalogue.",
    )
    y = first.bullet(
        y,
        "Point of sale: record a product and quantity, calculate the sale total, "
        "record the staff username, and reduce stock in the same transaction. "
        "Requests exceeding available stock are rejected.",
    )
    y = first.bullet(
        y,
        "Restocking: flag products at or below their reorder threshold. The "
        "suggested quantity targets max(2 x reorder threshold, 10 units), less "
        "current stock, with a minimum suggestion of one unit. Estimated cost "
        "uses the product price.",
    )
    y = first.bullet(
        y,
        "Shop overview: review recorded revenue, sales count, product count, "
        "low-stock count, inventory value, and sales history.",
    )

    first.rect(MARGIN, 70, PAGE_WIDTH - 2 * MARGIN, 50, PALE)
    first.text(MARGIN + 14, 101, "INTENDED VALUE", 8, "F2", TEAL)
    first.text(
        MARGIN + 14,
        84,
        "More consistent records, earlier low-stock visibility, and better-informed restock planning.",
        9,
        "F1",
        INK,
    )
    first.footer()

    second = Page(2)
    second.text(MARGIN, 724, "Implementation and evaluation", 20, "F2", INK)
    second.text(MARGIN, 704, "A simple client-to-service architecture keeps shop workflows connected.", 9, "F1", MUTED)

    boxes = [
        (MARGIN, "SHOP INTERFACE", "React 18", "Dashboard, inventory, POS"),
        (229, "APPLICATION API", "Spring Boot 3", "Java 21, JWT, business rules"),
        (408, "DATA STORE", "JPA database", "H2 locally; PostgreSQL on Render"),
    ]
    for x, label, title, detail in boxes:
        second.rect(x, 615, 154, 68, PALE)
        second.text(x + 10, 666, label, 7.5, "F2", TEAL)
        second.text(x + 10, 646, title, 12, "F2", INK)
        second.text(x + 10, 629, detail, 7.2, "F1", MUTED)
    second.rule(207, 649, 222, 649, TEAL, 1.4)
    second.rule(386, 649, 401, 649, TEAL, 1.4)

    y = second.section(584, "04", "Technology and deployment")
    y = second.paragraph(
        MARGIN,
        y,
        "The responsive React 18 and Tailwind CSS interface is bundled with Vite "
        "and served by a Java 21 / Spring Boot 3 application. REST APIs handle authentication, product "
        "management, sales, restock suggestions, and dashboard summaries. "
        "Spring Data JPA provides persistence. H2 supports local development "
        "and tests; the Render Blueprint provisions PostgreSQL for deployment.",
        size=9.6,
        leading=13,
    )

    y = second.section(y - 1, "05", "Access and safeguards")
    y = second.bullet(
        y,
        "JWT-based authentication and BCrypt password encoding.",
        size=9.2,
        leading=12,
    )
    y = second.bullet(
        y,
        "Owner permissions include product create, update, delete, and restock; "
        "owner and staff roles can record sales.",
        size=9.2,
        leading=12,
    )
    y = second.bullet(
        y,
        "Transactional sale processing checks available stock before decrementing "
        "it and saving the sale.",
        size=9.2,
        leading=12,
    )
    y = second.bullet(
        y,
        "Production owner credentials and JWT configuration are supplied through "
        "environment variables; demo accounts are not seeded in production.",
        size=9.2,
        leading=12,
    )

    y = second.section(y - 1, "06", "Evaluation and reviewer access")
    y = second.paragraph(
        MARGIN,
        y,
        "Automated verification: mvn -o test passed all 13 tests on 13 November 2026. "
        "A regression test confirms the production JavaScript and CSS load without "
        "authentication.",
        size=9.2,
        leading=12,
    )
    y = second.paragraph(
        MARGIN,
        y,
        "The sign-in page outage was caused by Spring Security returning 403 for "
        "frontend files under /assets/. The path is now permitted so the login "
        "interface can load; shop APIs still require authentication.",
        size=9.2,
        leading=12,
    )
    second.text(MARGIN, y, "Public landing page (overview):", 8.6)
    landing_url = (
        "https://romeo725.github.io/Spaza-Stock-Sales-Tracker/"
        "src/main/resources/static/landing.html"
    )
    y = second.hyperlink(MARGIN, y - 12, landing_url) - 5
    second.text(MARGIN, y, "Public app sign-in (Codespaces; available while active):", 8.6)
    app_url = "https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/"
    y = second.hyperlink(MARGIN, y - 12, app_url) - 5
    y = second.paragraph(
        MARGIN,
        y,
        "Local app URL: http://localhost:8080. Start it with mvn spring-boot:run; "
        "localhost is reachable only from the computer running the app.",
        size=9.2,
        leading=12,
    )
    y = second.bullet(
        y,
        "Local owner demo: username owner; password password123.",
        size=9.2,
        leading=12,
    )
    y = second.bullet(
        y,
        "Local staff demo: username staff; password password123.",
        size=9.2,
        leading=12,
    )
    y = second.paragraph(
        MARGIN,
        y,
        "These demo credentials are for local evaluation only. GitHub Pages hosts "
        "the static landing page; it does not host the Spring API.",
        size=9.2,
        leading=12,
        color=MUTED,
    )

    second.rect(MARGIN, 70, PAGE_WIDTH - 2 * MARGIN, 53, PALE)
    second.text(MARGIN + 14, 104, "NEXT STEPS", 8, "F2", TEAL)
    second.text(
        MARGIN + 14,
        87,
        "Potential extensions: barcode scanning, supplier ordering workflows, and demand forecasting.",
        8.8,
        "F1",
        INK,
    )
    second.text(
        MARGIN + 14,
        74,
        "These are roadmap ideas, not features in the current implementation.",
        8,
        "F1",
        MUTED,
    )
    second.footer()
    return [first, second]


def make_pdf(pages):
    objects = [
        b"<< /Type /Catalog /Pages 2 0 R >>",
        None,
        b"<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>",
        b"<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>",
    ]
    page_ids = []
    for page in pages:
        content = ("\n".join(page.commands) + "\n").encode("ascii")
        content_id = len(objects) + 1
        objects.append(
            b"<< /Length "
            + str(len(content)).encode("ascii")
            + b" >>\nstream\n"
            + content
            + b"endstream"
        )
        annotation_ids = []
        for x1, y1, x2, y2, url in page.links:
            annotation_id = len(objects) + 1
            objects.append(
                (
                    f"<< /Type /Annot /Subtype /Link /Rect "
                    f"[{x1:.1f} {y1:.1f} {x2:.1f} {y2:.1f}] "
                    f"/Border [0 0 0] /A << /S /URI /URI ({pdf_text(url)}) >> >>"
                ).encode("ascii")
            )
            annotation_ids.append(annotation_id)
        page_id = len(objects) + 1
        annotations = (
            f"/Annots [{' '.join(f'{item} 0 R' for item in annotation_ids)}] "
            if annotation_ids
            else ""
        )
        objects.append(
            (
                f"<< /Type /Page /Parent 2 0 R /MediaBox [0 0 {PAGE_WIDTH} {PAGE_HEIGHT}] "
                f"/Resources << /Font << /F1 3 0 R /F2 4 0 R >> >> "
                f"/Contents {content_id} 0 R {annotations}>>"
            ).encode("ascii")
        )
        page_ids.append(page_id)
    objects[1] = (
        f"<< /Type /Pages /Kids [{' '.join(f'{item} 0 R' for item in page_ids)}] "
        f"/Count {len(page_ids)} >>"
    ).encode("ascii")
    info_id = len(objects) + 1
    objects.append(
        b"<< /Title (Spaza Stock and Sales Tracker - Solution Brief) "
        b"/Author (Romeo Mduduzi Mangena) "
        b"/Subject (Samsung Innovation Internship 2027 - Route A) >>"
    )

    output = bytearray(b"%PDF-1.4\n%\xe2\xe3\xcf\xd3\n")
    offsets = [0]
    for index, obj in enumerate(objects, 1):
        offsets.append(len(output))
        output.extend(f"{index} 0 obj\n".encode("ascii"))
        output.extend(obj)
        output.extend(b"\nendobj\n")
    xref_offset = len(output)
    output.extend(f"xref\n0 {len(objects) + 1}\n".encode("ascii"))
    output.extend(b"0000000000 65535 f \n")
    for offset in offsets[1:]:
        output.extend(f"{offset:010d} 00000 n \n".encode("ascii"))
    output.extend(
        (
            f"trailer\n<< /Size {len(objects) + 1} /Root 1 0 R /Info {info_id} 0 R >>\n"
            f"startxref\n{xref_offset}\n%%EOF\n"
        ).encode("ascii")
    )
    OUTPUT.write_bytes(output)


if __name__ == "__main__":
    make_pdf(build_pages())
    print(OUTPUT)
