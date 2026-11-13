#!/usr/bin/env python3
"""Generate a PowerPoint walkthrough for the Spaza Stock & Sales Tracker."""

from pathlib import Path

from pptx import Presentation
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE
from pptx.enum.text import MSO_ANCHOR, PP_ALIGN
from pptx.util import Inches, Pt


ROOT = Path(__file__).resolve().parent
OUTPUT = ROOT / "Romeo_Mduduzi_Mangena_Spaza_Stock_Sales_Tracker_Site_Guide.pptx"
APP_URL = "https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/"
LANDING_URL = (
    "https://romeo725.github.io/Spaza-Stock-Sales-Tracker/"
    "src/main/resources/static/landing.html"
)

W = 13.333
H = 7.5
BG = "F4F8F7"
WHITE = "FFFFFF"
INK = "152B2A"
MUTED = "526967"
TEAL = "0F766E"
DEEP = "104B46"
MINT = "DDF4EE"
LINE = "D7E4E1"
AMBER = "F2A900"
PALE_AMBER = "FFF4D8"
RED = "B42318"
PALE_RED = "FEE9E7"
BLUE = "2E5AAC"
PALE_BLUE = "EAF0FF"


def color(hex_value):
    return RGBColor.from_string(hex_value)


def rect(slide, x, y, w, h, fill, rounded=True, line=None):
    shape = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE if rounded else MSO_SHAPE.RECTANGLE,
        Inches(x),
        Inches(y),
        Inches(w),
        Inches(h),
    )
    shape.fill.solid()
    shape.fill.fore_color.rgb = color(fill)
    shape.line.color.rgb = color(line or fill)
    return shape


def textbox(slide, x, y, w, h, text, size=16, fill=INK, bold=False,
            font="Aptos", align=PP_ALIGN.LEFT, valign=MSO_ANCHOR.TOP,
            margin=0.02):
    box = slide.shapes.add_textbox(Inches(x), Inches(y), Inches(w), Inches(h))
    tf = box.text_frame
    tf.clear()
    tf.word_wrap = True
    tf.margin_left = Inches(margin)
    tf.margin_right = Inches(margin)
    tf.margin_top = Inches(margin)
    tf.margin_bottom = Inches(margin)
    tf.vertical_anchor = valign
    paragraph = tf.paragraphs[0]
    paragraph.alignment = align
    run = paragraph.add_run()
    run.text = text
    run.font.name = font
    run.font.size = Pt(size)
    run.font.bold = bold
    run.font.color.rgb = color(fill)
    return box


def card(slide, x, y, w, h, title, body, accent=TEAL, body_size=15,
         fill=WHITE):
    rect(slide, x, y, w, h, fill, line=LINE)
    rect(slide, x, y, 0.09, h, accent, rounded=False)
    textbox(slide, x + 0.24, y + 0.18, w - 0.42, 0.34, title,
            size=17, fill=INK, bold=True)
    textbox(slide, x + 0.24, y + 0.62, w - 0.43, h - 0.77, body,
            size=body_size, fill=MUTED)


def bullet(slide, x, y, w, text, size=15, accent=TEAL, h=0.55):
    dot = slide.shapes.add_shape(
        MSO_SHAPE.OVAL, Inches(x), Inches(y + 0.10), Inches(0.12), Inches(0.12)
    )
    dot.fill.solid()
    dot.fill.fore_color.rgb = color(accent)
    dot.line.color.rgb = color(accent)
    textbox(slide, x + 0.24, y, w - 0.24, h, text, size=size, fill=INK)


def step(slide, x, y, number, title, body, w=5.8, h=1.12, accent=TEAL):
    circle = slide.shapes.add_shape(
        MSO_SHAPE.OVAL, Inches(x), Inches(y + 0.04), Inches(0.48), Inches(0.48)
    )
    circle.fill.solid()
    circle.fill.fore_color.rgb = color(accent)
    circle.line.color.rgb = color(accent)
    textbox(slide, x, y + 0.065, 0.48, 0.36, str(number), size=16,
            fill=WHITE, bold=True, align=PP_ALIGN.CENTER,
            valign=MSO_ANCHOR.MIDDLE)
    textbox(slide, x + 0.65, y, w - 0.65, 0.31, title, size=17,
            fill=INK, bold=True)
    textbox(slide, x + 0.65, y + 0.38, w - 0.7, h - 0.35, body,
            size=14, fill=MUTED)


def new_slide(prs, title, section, number):
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    slide.background.fill.solid()
    slide.background.fill.fore_color.rgb = color(BG)
    textbox(slide, 0.62, 0.30, 7.6, 0.24, section.upper(),
            size=9, fill=TEAL, bold=True)
    textbox(slide, 0.62, 0.64, 12.0, 0.55, title,
            size=27, fill=INK, bold=True)
    rect(slide, 0.62, 1.32, 12.05, 0.015, LINE, rounded=False)
    rect(slide, 0.62, 7.12, 12.05, 0.012, LINE, rounded=False)
    textbox(slide, 0.62, 7.20, 7.8, 0.18,
            "SPAZA STOCK & SALES TRACKER  |  ROMEO MDUduzi MANGENA".upper(),
            size=8, fill=MUTED)
    textbox(slide, 12.1, 7.18, 0.55, 0.2, f"{number:02d}",
            size=9, fill=TEAL, bold=True, align=PP_ALIGN.RIGHT)
    return slide


def add_link(slide, x, y, w, h, text, url, size=14):
    box = textbox(slide, x, y, w, h, text, size=size, fill=TEAL, bold=True)
    run = box.text_frame.paragraphs[0].runs[0]
    run.hyperlink.address = url
    return box


def create_deck():
    prs = Presentation()
    prs.slide_width = Inches(W)
    prs.slide_height = Inches(H)
    prs.core_properties.title = "Spaza Stock & Sales Tracker - Site Walkthrough"
    prs.core_properties.author = "Romeo Mduduzi Mangena"
    prs.core_properties.subject = "Step-by-step guide from login through sign-out"

    # 1. Cover
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    slide.background.fill.solid()
    slide.background.fill.fore_color.rgb = color(DEEP)
    rect(slide, 9.65, 0, 3.683, H, TEAL, rounded=False)
    rect(slide, 10.15, 1.08, 2.45, 1.08, "176D64")
    rect(slide, 10.55, 2.36, 1.95, 1.08, "1B8276")
    rect(slide, 10.95, 3.64, 1.55, 1.08, "249486")
    textbox(slide, 0.85, 0.62, 7.7, 0.35,
            "STEP-BY-STEP USER GUIDE", size=12, fill="9FE5D5", bold=True)
    textbox(slide, 0.85, 1.35, 8.0, 1.45,
            "Spaza Stock &\nSales Tracker", size=35, fill=WHITE, bold=True)
    textbox(slide, 0.88, 3.15, 7.65, 0.88,
            "From opening the live demo and signing in to recording sales, "
            "restocking, reviewing history, and signing out.",
            size=18, fill="E0F2EF")
    textbox(slide, 0.88, 4.60, 7.4, 0.38,
            "Prepared by Romeo Mduduzi Mangena", size=16, fill=WHITE, bold=True)
    textbox(slide, 0.88, 5.28, 7.5, 0.25,
            "PUBLIC APP SIGN-IN  |  AVAILABLE WHILE THE CODESPACE IS RUNNING",
            size=9, fill="B7E8DE", bold=True)
    add_link(slide, 0.88, 5.58, 8.4, 0.32, APP_URL, APP_URL, size=10)
    textbox(slide, 0.88, 6.00, 7.5, 0.25,
            "PUBLIC LANDING PAGE",
            size=9, fill="B7E8DE", bold=True)
    add_link(slide, 0.88, 6.30, 8.4, 0.32, LANDING_URL, LANDING_URL, size=9)
    textbox(slide, 0.88, 7.10, 7.6, 0.2,
            "Owner and staff workflows included", size=9, fill="B7E8DE")

    # 2. Login first
    slide = new_slide(prs, "Sign in to your shop", "01  |  Login first", 2)
    card(slide, 0.72, 1.7, 5.8, 1.52, "Open the login screen",
         "Open the public app link directly, or start on the landing page and select Sign in.")
    add_link(slide, 1.02, 2.66, 5.1, 0.25, "Open the app sign-in", APP_URL, size=12)
    add_link(slide, 1.02, 2.96, 5.1, 0.22, "Open the public landing page", LANDING_URL, size=10)
    card(slide, 6.78, 1.7, 5.8, 1.52, "Keep the demo available",
         "The port is public while the Codespace is running. The app still requires "
         "a shop account to sign in.", accent=AMBER)
    card(slide, 0.72, 3.5, 5.8, 1.42, "Owner demo account",
         "Username: owner\nPassword: password123", accent=TEAL, body_size=16)
    card(slide, 6.78, 3.5, 5.8, 1.42, "Staff demo account",
         "Username: staff\nPassword: password123", accent=BLUE, body_size=16)
    rect(slide, 0.72, 5.2, 11.86, 1.35, PALE_AMBER, line=PALE_AMBER)
    textbox(slide, 0.98, 5.39, 11.25, 0.75,
            "Enter the username and password, then select Sign in. Wait for the Dashboard. "
            "A successful login shows your name and role in the green bar at the top. "
            "If sign-in fails, check both fields and try again.",
            size=14, fill=INK, bold=True)
    textbox(slide, 0.98, 6.25, 11.25, 0.22,
            "Demo credentials only; never reuse these for a real shop or production account.",
            size=10, fill=MUTED)

    # 3. Dashboard
    slide = new_slide(prs, "Start at the dashboard", "02  |  Shop overview", 3)
    metric_cards = [
        ("Sales revenue", "Total recorded sales"),
        ("Products", "Number of inventory items"),
        ("Low stock", "Items needing attention"),
        ("Stock value", "Estimated value of current stock"),
    ]
    for i, (head, detail) in enumerate(metric_cards):
        x = 0.72 + i * 3.0
        rect(slide, x, 1.72, 2.78, 1.14, WHITE, line=LINE)
        textbox(slide, x + 0.16, 1.93, 2.46, 0.29, head, size=15, fill=TEAL, bold=True)
        textbox(slide, x + 0.16, 2.31, 2.47, 0.36, detail, size=11, fill=MUTED)
    card(slide, 0.72, 3.18, 5.8, 1.55, "Low-stock alert",
         "When products are at or below their reorder threshold, the dashboard displays "
         "an alert. Choose View Restock Suggestions to open Smart Restock.", accent=AMBER)
    card(slide, 6.78, 3.18, 5.8, 1.55, "Recent sales and quick sale",
         "Review the five most recent sales. The Quick Sale Recorder lets you select a "
         "product, enter a quantity, and record a sale without leaving the dashboard.")
    rect(slide, 0.72, 5.18, 11.86, 1.0, PALE_BLUE, line=PALE_BLUE)
    textbox(slide, 0.98, 5.43, 11.25, 0.5,
            "Main navigation: Dashboard  |  Inventory  |  Point of Sale  |  Smart Restock  |  Sales History",
            size=15, fill=INK, bold=True)

    # 5. Inventory
    slide = new_slide(prs, "Find products and check stock", "03  |  Inventory", 4)
    step(slide, 0.9, 1.72, 1, "Open Inventory",
         "Choose Inventory from the navigation bar.", w=5.55)
    step(slide, 6.85, 1.72, 2, "Search and filter",
         "Search by product name or category. Use the category selector to narrow the list.",
         w=5.55)
    step(slide, 0.9, 3.24, 3, "Read the product row",
         "Review product name, category, price, quantity, reorder level, and status.",
         w=5.55)
    step(slide, 6.85, 3.24, 4, "Understand stock status",
         "Green means in stock; amber means low stock; red means out of stock.",
         w=5.55)
    card(slide, 0.9, 5.08, 5.55, 1.1, "No results?",
         "Clear the search text or choose a different category.", accent=BLUE, body_size=13)
    card(slide, 6.85, 5.08, 5.55, 1.1, "Role note",
         "Owner accounts see Edit and Delete actions. Staff accounts do not.",
         accent=AMBER, body_size=13)

    # 6. Product management
    slide = new_slide(prs, "Add, edit, or remove a product", "04  |  Owner tasks", 5)
    card(slide, 0.72, 1.7, 5.78, 2.08, "Add a product",
         "1. In Inventory, choose Add New Product.\n"
         "2. Enter product name, category, price, stock quantity, reorder threshold, and supplier.\n"
         "3. Choose Save Product and check for the success message.",
         body_size=14)
    card(slide, 6.78, 1.7, 5.8, 2.08, "Edit a product",
         "1. Find the product in Inventory.\n"
         "2. Choose Edit and update the required fields.\n"
         "3. Save Product. The inventory list refreshes with the saved values.",
         accent=BLUE, body_size=14)
    card(slide, 0.72, 4.08, 5.78, 1.72, "Delete a product",
         "Choose Delete on the product row and confirm only if you intend to remove it. "
         "Deletion changes the shop catalogue.", accent=RED, body_size=14)
    rect(slide, 6.78, 4.08, 5.8, 1.72, PALE_AMBER, line=PALE_AMBER)
    textbox(slide, 7.02, 4.32, 5.3, 0.32, "OWNER ACCESS REQUIRED", size=13,
            fill=INK, bold=True)
    textbox(slide, 7.02, 4.77, 5.25, 0.73,
            "Staff accounts can browse products and record sales, but cannot add, edit, "
            "delete, or restock products.", size=14, fill=MUTED)

    # 7. Point of sale
    slide = new_slide(prs, "Record a sale", "05  |  Point of sale", 6)
    card(slide, 0.72, 1.72, 5.78, 1.68, "Option A: Quick Sale Recorder",
         "From Dashboard, choose a product, enter quantity, and select Record Sale Now.",
         body_size=15)
    card(slide, 6.78, 1.72, 5.8, 1.68, "Option B: Point of Sale",
         "Open Point of Sale, select an item, and enter Quantity Sold. The screen shows "
         "unit price, available stock, and total amount due.", accent=BLUE, body_size=15)
    step(slide, 0.92, 3.86, 1, "Check the total",
         "Confirm the selected product, quantity, and displayed total before submitting.",
         w=5.45, h=1.12)
    step(slide, 6.85, 3.86, 2, "Complete the sale",
         "Choose Record Sale Now or Complete Sale & Deduct Stock, depending on the screen.",
         w=5.45, h=1.12)
    rect(slide, 0.72, 5.55, 11.86, 0.86, MINT, line=MINT)
    textbox(slide, 0.98, 5.77, 11.2, 0.46,
            "A successful sale is recorded and stock is reduced. Out-of-stock items cannot be selected; "
            "sales exceeding available stock are rejected.",
            size=13, fill=DEEP, bold=True)

    # 8. Restock
    slide = new_slide(prs, "Review and apply restock suggestions", "06  |  Smart Restock", 7)
    step(slide, 0.9, 1.68, 1, "Open Smart Restock",
         "Choose Smart Restock in the top navigation, or use the dashboard alert.",
         w=5.55)
    step(slide, 6.85, 1.68, 2, "Review each suggestion",
         "Check urgency, current inventory, reorder level, supplier, suggested quantity, "
         "and estimated supplier cost.", w=5.55)
    rect(slide, 0.9, 3.36, 5.55, 1.34, PALE_BLUE, line=PALE_BLUE)
    textbox(slide, 1.16, 3.57, 5.0, 0.3, "HOW THE SUGGESTION IS CALCULATED",
            size=12, fill=BLUE, bold=True)
    textbox(slide, 1.16, 3.98, 5.0, 0.54,
            "Target = max(2 x reorder level, 10)\n"
            "Suggested order = max(target - current stock, 1)",
            size=14, fill=INK, bold=True)
    card(slide, 6.85, 3.36, 5.55, 1.34, "Apply a suggestion",
         "The 1-Click Restock button immediately adds the suggested quantity to stock. "
         "Review it before clicking.", accent=AMBER, body_size=14)
    rect(slide, 0.9, 5.12, 11.5, 0.95, PALE_AMBER, line=PALE_AMBER)
    textbox(slide, 1.15, 5.37, 10.95, 0.45,
            "After restocking, look for the success message and confirm the new quantity in Inventory.",
            size=14, fill=INK, bold=True)

    # 9. Sales history
    slide = new_slide(prs, "Review sales history", "07  |  Reports", 8)
    card(slide, 0.72, 1.72, 5.78, 1.78, "Open Sales History",
         "Choose Sales History in the navigation. The table lists every recorded transaction "
         "available in the current demo database.")
    card(slide, 6.78, 1.72, 5.8, 1.78, "Read the transaction details",
         "Review transaction ID, product, quantity, unit price, total revenue, date and time, "
         "and cashier/staff username.", accent=BLUE)
    textbox(slide, 0.92, 4.08, 11.2, 0.4, "For each sale, use this quick check:",
            size=18, fill=INK, bold=True)
    bullet(slide, 0.98, 4.72, 11.0,
           "Does the product and quantity match the sale you just recorded?", size=15)
    bullet(slide, 0.98, 5.34, 11.0,
           "Does the total revenue equal unit price multiplied by quantity?", size=15)
    bullet(slide, 0.98, 5.96, 11.0,
           "Does the cashier name match the account used to sign in?", size=15)

    # 10. Account roles
    slide = new_slide(prs, "Use the right account for each task", "08  |  Roles and access", 9)
    card(slide, 0.72, 1.72, 5.78, 2.22, "Owner",
         "Can view shop data, manage products, add/edit/delete catalogue entries, restock, "
         "and record sales.\n\nDemo username: owner", accent=TEAL, body_size=15)
    card(slide, 6.78, 1.72, 5.8, 2.22, "Staff",
         "Can view shop data and record sales. Product management and restock controls are "
         "not available.\n\nDemo username: staff", accent=BLUE, body_size=15)
    rect(slide, 0.72, 4.32, 11.86, 1.45, PALE_BLUE, line=PALE_BLUE)
    textbox(slide, 0.98, 4.58, 11.1, 0.34, "To switch accounts", size=17,
            fill=INK, bold=True)
    textbox(slide, 0.98, 5.05, 11.0, 0.55,
            "Choose Sign out in the top bar, then sign in again with the other username and its password. "
            "There is no one-click role switch.",
            size=15, fill=MUTED)

    # 11. End-to-end exercise
    slide = new_slide(prs, "Practice the complete shop workflow", "09  |  Guided run-through", 10)
    steps = [
        ("Open and sign in", "Use the public app link and sign in as owner."),
        ("Check the overview", "Review the dashboard totals and any low-stock alert."),
        ("Inspect inventory", "Search for a product and read its stock status."),
        ("Record a sale", "Use Quick Sale or Point of Sale; verify the total first."),
        ("Verify the update", "Confirm stock fell and the transaction appears in Sales History."),
        ("Restock if needed", "Review the suggested quantity, apply it, then check Inventory."),
        ("Finish", "Sign out from the top bar when the walkthrough is complete."),
    ]
    y = 1.62
    for idx, (title, body) in enumerate(steps, start=1):
        step(slide, 0.9, y, idx, title, body, w=11.2, h=0.68,
             accent=TEAL if idx < 7 else BLUE)
        y += 0.72

    # 11. Troubleshooting
    slide = new_slide(prs, "Troubleshoot common issues", "10  |  Quick help", 11)
    card(slide, 0.72, 1.7, 5.78, 1.54, "The app link does not load",
         "Confirm the Codespace is running. The blank login was caused by a 403 on "
         "JavaScript/CSS files under /assets/**; that path is now allowed. Refresh the page.",
         accent=AMBER, body_size=13)
    card(slide, 6.78, 1.7, 5.8, 1.54, "Sign-in fails",
         "Check the username and password. Use the owner or staff demo account shown in this guide.",
         accent=RED, body_size=14)
    card(slide, 0.72, 3.54, 5.78, 1.54, "A product is not available for sale",
         "It may be out of stock. Check the Inventory page or review Smart Restock.",
         accent=BLUE, body_size=14)
    card(slide, 6.78, 3.54, 5.8, 1.54, "A control is missing",
         "Product management and restock actions are owner-only. Sign out and use the owner account.",
         accent=TEAL, body_size=14)
    rect(slide, 0.72, 5.44, 11.86, 0.92, PALE_BLUE, line=PALE_BLUE)
    textbox(slide, 0.98, 5.58, 11.15, 0.58,
            "Resolved: /assets/** now loads the login UI (JS/CSS 200); shop APIs remain protected. "
            "All 13 tests pass.",
            size=13, fill=INK, bold=True)

    # 12. Logout
    slide = new_slide(prs, "Sign out and finish the visit", "11  |  Logout", 12)
    step(slide, 0.9, 1.78, 1, "Finish or verify your work",
         "Check the dashboard, inventory, and Sales History for the changes you made.",
         w=5.55)
    step(slide, 6.85, 1.78, 2, "Choose Sign out",
         "Use the Sign out button in the green account bar at the top of the app.",
         w=5.55)
    step(slide, 0.9, 3.48, 3, "Confirm you are signed out",
         "The app returns to the shop sign-in screen. Your account token is removed from "
         "the browser session.", w=5.55)
    step(slide, 6.85, 3.48, 4, "Close the browser if done",
         "If another person will use the same device, leave the app signed out before handing it over.",
         w=5.55)
    rect(slide, 0.9, 5.35, 11.5, 0.92, MINT, line=MINT)
    textbox(slide, 1.16, 5.58, 10.95, 0.45,
            "To change roles, sign out first, then sign in again with the other account.",
            size=16, fill=DEEP, bold=True)

    prs.save(OUTPUT)
    return OUTPUT


if __name__ == "__main__":
    print(create_deck())
