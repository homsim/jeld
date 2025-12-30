#!/usr/bin/python3
"""
Generates a bindings.xjb to include all the xsd-files in this directory.
Only needs to be executed when new schemas are added here.
"""

from pathlib import Path

xsd_dir = Path(__file__).resolve().parent

bindings_fn = xsd_dir / "bindings.xjb"
header = """<jaxb:bindings version="3.0"
    xmlns:jaxb="https://jakarta.ee/xml/ns/jaxb"
    xmlns:xs="http://www.w3.org/2001/XMLSchema">
"""
entry = """
    <jaxb:bindings schemaLocation="{camt_fn}">
        <jaxb:schemaBindings>
            <jaxb:package name="com.homsim.jeld.data.parse.{camt_fn_underscore}"/>
        </jaxb:schemaBindings>
    </jaxb:bindings>
"""
footer = """
</jaxb:bindings>"""

with open(bindings_fn, "w") as f:
    f.write(header)
    for xsd in xsd_dir.glob("*.xsd"):
        xsd_name = xsd.name  # Get just the filename
        f.write(
            entry.format(
                camt_fn=xsd_name,
                camt_fn_underscore=xsd_name.replace(".xsd", "").replace(".", "_")
            )
        )
    f.write(footer)