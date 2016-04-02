<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="base">
    <layout:put block="header" type="REPLACE">
        <h2>This is an example about layout management with JSP Template Inheritance</h2>
    </layout:put>
    <layout:put block="contents">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin porta,
        augue ut ornare sagittis, diam libero facilisis augue, quis accumsan enim velit a mauris.
        Ut eleifend elit ante, sit amet suscipit leo lobortis eu. Quisque vitae lorem feugiat,
        lacinia nulla eu, interdum eros. Ut dignissim tincidunt nisl ac iaculis.
        Praesent consectetur arcu vitae tellus scelerisque venenatis.
        Morbi vel leo eros. In id libero ultricies, laoreet enim et, tempor magna.
        Vestibulum lorem velit, accumsan id purus at, lobortis fermentum diam.
        Aenean nec placerat elit. Aenean vel sem arcu.
    </layout:put>
</layout:extends>