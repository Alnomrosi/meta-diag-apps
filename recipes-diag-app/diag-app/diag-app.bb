SUMMARY = "Application Diagnostics Microservice"
DESCRIPTION = "A diagnostics middleware between SOVD server Applications"

LICENSE = "Apache-2.0"
SRC_URI = "git://github.com/Alnomrosi/Simple-Apps-Diag-Microservice.git;protocol=https;branch=main \
        file://diag-app.service"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ba5044c64ef53cb0189c9546081e228"

inherit systemd

RDEPENDS:${PN} += " \
    python3-flask \
    python3-click \
    python3-itsdangerous \
    python3-jinja2 \
    python3-markupsafe \
    python3-werkzeug \
    python3-blinker \
    python3-importlib-metadata \
    python3-pyyaml \
    python3-zipp \
    python3-pydantic \
    python3-pydantic-core \
    python3-annotated-types \
    python3-typing-extensions \
    python3-requests \
"

SYSTEMD_SERVICE:${PN} = "diag-app.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile() {
    # Optional: precompile Python bytecode
    python3 -m compileall ${S}/diag_app
}

# install the source code in bindir
do_install(){

    # Ensure no Windows line endings in all Python files
    find ${S} -type f -name "*.py" -exec sed -i 's/\r$//' {} +
    
    # Install the diag_app package
    install -d ${D}${libdir}/python3/site-packages/diag_app
    cp -r ${S}/diag_app/* ${D}${libdir}/python3/site-packages/diag_app/

    # Install diag_app data_model folder in home dir
    install -d ${D}/var/diagnostics/apps
    cp -r ${S}/diag_app/data_model ${D}/var/diagnostics/apps/

    # Install main.py as executable script
    install -d ${D}${bindir}
    install -m 0755 ${S}/diag_app/main.py ${D}${bindir}/apps_diag

    # Ensure correct line endings and permissions for executable
    sed -i 's/\r$//' ${D}${bindir}/apps_diag
    chmod +x ${D}${bindir}/apps_diag

    # Install systemd unit
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/diag-app.service ${D}${systemd_system_unitdir}
}



FILES:${PN} += "${libdir}/python3/site-packages/diag_app ${systemd_system_unitdir}"
FILES:${PN} += "/var/diagnostics"
INSANE_SKIP:${PN} += "buildpaths"