<?xml version="1.0" encoding="UTF-8"?>
<!--
    b2_to_b3.xsl — BEAST2 XML → BEAST3 XML structural transformer
    XSLT 1.0 (lxml compatible)

    Invoked by convert_b2_to_b3.py which runs a Python pre-pass first:
      - Annotates <parameter> elements with _b3spec, _b3domain attributes
      - Annotates <distribution spec="*Prior"> elements with _b3prior_type
      - Passes the deprecated-class rename map via the `rename_map` parameter
        as a pipe-separated string:  OldFQN|NewFQN|OldFQN2|NewFQN2|...
        (XSLT 1.0 has no maps; the Python script does attribute-level lookup
         before calling the stylesheet and stamps _b3spec on each element.)

    Templates (in priority order):
      T1  <beast> root        — version + namespace
      T2  <parameter>         — RealParameter → typed param  (pre-annotated)
      T3  <distribution>      — Prior flatten / OneOnX / IID (pre-annotated)
      T4  <operator>          — ScaleOperator split, Uniform tree op
      T5  @spec / @type / @class  — simple .spec. rename  (pre-annotated _b3spec)
      T6  identity            — copy everything else unchanged
-->
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <!-- cdata-section-elements: serialise <subtemplate> text content back into
       <![CDATA[...]]> instead of entity-escaped text. FxTemplate <subtemplate>
       elements hold an embedded runnable-analysis XML fragment as CDATA; without
       this, the identity-copied text still round-trips to the same string once
       re-parsed (CDATA and escaped text are equivalent in the XML data model),
       but is unreadable and undiffable as one long escaped line. -->
  <xsl:output method="xml" encoding="UTF-8" indent="yes" cdata-section-elements="subtemplate"/>

  <!-- ═══════════════════════════════════════════════════════════════════
       T1 — Root <beast> element
       Always sets version="2.8" (BEAST3 requires it, including FxTemplates).
       Namespace is replaced with the legacy/core package list only for
       runnable example XMLs; FxTemplates (_b3fxtemplate set) keep their
       original, broader namespace (e.g. beastfx.app.beauti) unchanged.
       ═══════════════════════════════════════════════════════════════════ -->
  <!-- T7 — Strip <map name="..."> elements (B2 short-name aliases; replaced by FQNs) -->
  <xsl:template match="map"/>

  <!-- Example XML: version="2.8" + namespace rewritten to the legacy/core list -->
  <xsl:template match="/beast[@_b3version and not(@_b3fxtemplate)]">
    <beast version="2.8">
      <!--
        Namespace contains only legacy/core packages that resolve non-deprecated
        short names (MCMC, CompoundDistribution, Exchange, WilsonBalding, etc.).
        All deprecated/renamed classes use full spec FQNs — no spec packages needed.
      -->
      <xsl:attribute name="namespace">beast.core:beast.core.util:beast.evolution.alignment:beast.evolution.nuc:beast.evolution.operators:beast.evolution.sitemodel:beast.evolution.substitutionmodel:beast.evolution.tree.coalescent:beast.base.core:beast.base.evolution.alignment:beast.base.evolution.likelihood:beast.base.evolution.operator:beast.base.evolution.sitemodel:beast.base.evolution.substitutionmodel:beast.base.evolution.tree:beast.base.evolution.tree.coalescent:beast.base.inference:beast.base.inference.operator:beast.base.inference.util:beast.pkgmgmt</xsl:attribute>
      <xsl:apply-templates select="@*[name()!='version' and name()!='namespace' and not(starts-with(name(),'_b3'))]"/>
      <xsl:apply-templates select="node()"/>
    </beast>
  </xsl:template>

  <!-- FxTemplate: version="2.8" only — namespace kept exactly as authored -->
  <xsl:template match="/beast[@_b3version and @_b3fxtemplate]">
    <beast version="2.8">
      <xsl:apply-templates select="@*[name()!='version' and not(starts-with(name(),'_b3'))]"/>
      <xsl:apply-templates select="node()"/>
    </beast>
  </xsl:template>

  <!-- Defensive fallback: root element without _b3version (should not occur —
       prepass() always stamps it — kept in case the XSLT is ever invoked
       without the Python pre-pass). -->
  <xsl:template match="/beast[not(@_b3version)]">
    <beast>
      <xsl:apply-templates select="@*[not(starts-with(name(),'_b3'))]"/>
      <xsl:apply-templates select="node()"/>
    </beast>
  </xsl:template>

  <!-- ═══════════════════════════════════════════════════════════════════
       T2 — <parameter> with _b3spec annotation (RealParameter migration)
       Python pre-pass sets:
         _b3spec   = target spec class  (e.g. RealScalarParam)
         _b3domain = domain class name  (e.g. PositiveReal)
         _b3fqn    = full qualified name if needed
       ═══════════════════════════════════════════════════════════════════ -->
  <!-- T2: scalar/vector parameters — drop lower=/upper=/minordimension=, add domain=.
       dimension= is kept for vector types (_b3spec contains 'Vector') and integer
       simplex (_b3spec contains 'Simplex', e.g. IntSimplexParam).
       Boolean params (BoolScalarParam/BoolVectorParam) are handled by T2b. -->
  <xsl:template match="*[@_b3spec and @_b3domain
                          and @_b3domain!='simplex'
                          and @_b3domain!='boolean']">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*[not(name()='spec')
                                   and not(name()='lower')
                                   and not(name()='upper')
                                   and not(name()='minordimension')
                                   and not(name()='dimension'
                                           and not(contains(../@_b3spec,'Vector')
                                                   or contains(../@_b3spec,'Simplex')))
                                   and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec"><xsl:value-of select="@_b3spec"/></xsl:attribute>
      <xsl:attribute name="domain"><xsl:value-of select="@_b3domain"/></xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>

  <!-- T2b: BoolScalarParam / BoolVectorParam — no domain= input.
       dimension= is kept for BoolVectorParam (_b3spec contains 'Vector'). -->
  <xsl:template match="*[@_b3spec and @_b3domain='boolean']">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*[not(name()='spec')
                                   and not(name()='lower')
                                   and not(name()='upper')
                                   and not(name()='minordimension')
                                   and not(name()='dimension'
                                           and not(contains(../@_b3spec,'Vector')))
                                   and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec"><xsl:value-of select="@_b3spec"/></xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>

  <!-- T2s: SimplexParam — keep dimension= (required for expansion), drop lower=/upper=, no domain= -->
  <xsl:template match="*[@_b3spec and @_b3domain='simplex']">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*[not(name()='spec')
                                   and not(name()='lower')
                                   and not(name()='upper')
                                   and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec"><xsl:value-of select="@_b3spec"/></xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>

  <!-- ═══════════════════════════════════════════════════════════════════
       T3 — <distribution spec="*Prior"> variants
       Python pre-pass sets _b3prior_type on the element:
         flatten     → inline the inner distribution directly
         iid         → wrap with IID
         oneonx_pop  → replace with LogNormal(M=3,S=2.5) for popSize
         oneonx_kappa→ replace with LogNormal(M=1,S=0.5) for hky.kappa
       ═══════════════════════════════════════════════════════════════════ -->

  <!-- T3a: flatten Prior → inline inner distribution.
       Matches any element tag (distribution OR prior — BEAUti uses <prior> as
       a <map>-based alias for beast.base.inference.distribution.Prior).
       $inner selector covers three BEAST2 authoring styles:
         <distr spec="..."/>          — tag is 'distr'
         <distribution spec="..."/>   — tag is 'distribution'
         <LogNormal name="distr" ."/> — tag is class short name, name attr is 'distr'
       x= is converted to param= (BEAST3 distribution API uses param=). -->
  <xsl:template match="*[@_b3prior_type='flatten']">
    <xsl:variable name="inner" select="(*[local-name()='distr' or local-name()='distribution'] | *[@name='distr'])[1]"/>
    <distribution>
      <xsl:if test="@id">
        <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
      </xsl:if>
      <!-- Use _b3spec on inner if set, else its spec attr -->
      <xsl:choose>
        <xsl:when test="$inner/@_b3spec">
          <xsl:attribute name="spec"><xsl:value-of select="$inner/@_b3spec"/></xsl:attribute>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="spec"><xsl:value-of select="$inner/@spec"/></xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>
      <!-- x= → param= (BEAST3 API); keep param= if already present -->
      <xsl:if test="@x">
        <xsl:attribute name="param"><xsl:value-of select="@x"/></xsl:attribute>
      </xsl:if>
      <xsl:if test="@param">
        <xsl:attribute name="param"><xsl:value-of select="@param"/></xsl:attribute>
      </xsl:if>
      <!-- Copy inner distribution's attributes (excluding spec, name, _b3*) -->
      <xsl:apply-templates select="$inner/@*[not(name()='spec') and not(name()='name') and not(starts-with(name(),'_b3'))]"/>
      <!-- Copy inner distribution's children -->
      <xsl:apply-templates select="$inner/node()"/>
    </distribution>
  </xsl:template>

  <!-- T3b: IID — vector prior. Same element-tag and $inner fixes as T3a. -->
  <xsl:template match="*[@_b3prior_type='iid']">
    <xsl:variable name="inner" select="(*[local-name()='distr' or local-name()='distribution'] | *[@name='distr'])[1]"/>
    <distribution>
      <xsl:if test="@id">
        <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
      </xsl:if>
      <xsl:attribute name="spec">beast.base.spec.inference.distribution.IID</xsl:attribute>
      <!-- x= → param= (BEAST3 API) -->
      <xsl:if test="@x">
        <xsl:attribute name="param"><xsl:value-of select="@x"/></xsl:attribute>
      </xsl:if>
      <xsl:if test="@param">
        <xsl:attribute name="param"><xsl:value-of select="@param"/></xsl:attribute>
      </xsl:if>
      <xsl:apply-templates select="$inner"/>
    </distribution>
  </xsl:template>

  <!-- T3c: OneOnX on popSize → LogNormal(M=3,S=2.5) -->
  <xsl:template match="*[@_b3prior_type='oneonx_pop']">
    <distribution>
      <xsl:if test="@id"><xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute></xsl:if>
      <xsl:attribute name="spec">beast.base.spec.inference.distribution.LogNormal</xsl:attribute>
      <xsl:attribute name="param"><xsl:value-of select="@x"/></xsl:attribute>
      <M spec="beast.base.spec.inference.parameter.RealScalarParam" domain="Real" value="3.0"/>
      <S spec="beast.base.spec.inference.parameter.RealScalarParam" domain="PositiveReal" value="2.5"/>
    </distribution>
  </xsl:template>

  <!-- T3d: OneOnX on hky.kappa → LogNormal(M=1,S=0.5) -->
  <xsl:template match="*[@_b3prior_type='oneonx_kappa']">
    <distribution>
      <xsl:if test="@id"><xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute></xsl:if>
      <xsl:attribute name="spec">beast.base.spec.inference.distribution.LogNormal</xsl:attribute>
      <xsl:attribute name="param"><xsl:value-of select="@x"/></xsl:attribute>
      <M spec="beast.base.spec.inference.parameter.RealScalarParam" domain="Real" value="1.0"/>
      <S spec="beast.base.spec.inference.parameter.RealScalarParam" domain="PositiveReal" value="0.5"/>
    </distribution>
  </xsl:template>

  <!-- T3e: OneOnX with unknown parameter → LogNormal with conservative defaults -->
  <xsl:template match="*[@_b3prior_type='oneonx_generic']">
    <distribution>
      <xsl:if test="@id"><xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute></xsl:if>
      <xsl:attribute name="spec">beast.base.spec.inference.distribution.LogNormal</xsl:attribute>
      <xsl:attribute name="param"><xsl:value-of select="@x"/></xsl:attribute>
      <M spec="beast.base.spec.inference.parameter.RealScalarParam" domain="Real" value="1.0"/>
      <S spec="beast.base.spec.inference.parameter.RealScalarParam" domain="PositiveReal" value="1.0"/>
    </distribution>
  </xsl:template>

  <!-- ═══════════════════════════════════════════════════════════════════
       T4 — Operator transforms
       ═══════════════════════════════════════════════════════════════════ -->

  <!-- T4a: ScaleOperator with parameter= → inference ScaleOperator -->
  <xsl:template match="operator[contains(@spec,'ScaleOperator') and @parameter]
                       |operator[contains(@spec,'BactrianScaleOperator') and @parameter]">
    <operator>
      <xsl:apply-templates select="@*[not(name()='spec') and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec">beast.base.spec.inference.operator.ScaleOperator</xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </operator>
  </xsl:template>

  <!-- T4b: ScaleOperator with tree= → evolution ScaleTreeOperator -->
  <xsl:template match="operator[contains(@spec,'ScaleOperator') and @tree]
                       |operator[contains(@spec,'BactrianScaleOperator') and @tree]">
    <operator>
      <xsl:apply-templates select="@*[not(name()='spec') and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec">beast.base.spec.evolution.operator.ScaleTreeOperator</xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </operator>
  </xsl:template>

  <!-- T4c: Uniform tree operator — must use full legacy path -->
  <xsl:template match="operator[@spec='Uniform' and @tree]
                       |operator[contains(@spec,'inference.operator.Uniform') and @tree]">
    <operator>
      <xsl:apply-templates select="@*[not(name()='spec') and not(starts-with(name(),'_b3'))]"/>
      <xsl:attribute name="spec">beast.base.evolution.operator.Uniform</xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </operator>
  </xsl:template>

  <!-- T4d: SubtreeSlide → BactrianSubtreeSlide
       Drops the legacy 'gaussian' attribute: BactrianSubtreeSlide has no such Input.
       'size' is kept — it IS a valid Input on BactrianSubtreeSlide. -->
  <xsl:template match="*[@_b3spec='beast.base.evolution.operator.kernel.BactrianSubtreeSlide']">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*[not(name()='gaussian') and not(starts-with(name(),'_b3'))]"/>
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>

  <!-- ═══════════════════════════════════════════════════════════════════
       T5 — Simple .spec. rename on spec=/type=/class= attributes
       Python pre-pass stamps _b3spec on the ELEMENT when any of its
       spec/type/class attrs need renaming. The attribute template below
       replaces them.
       ═══════════════════════════════════════════════════════════════════ -->

  <!-- Element with a _b3spec annotation (simple rename) but not handled by T2-T4 above.
       T2 handles elements with _b3domain; T3 handles _b3prior_type; T4 handles operators. -->
  <xsl:template match="*[@_b3spec and not(@_b3domain)
                                  and not(@_b3prior_type)
                                  and not(contains(@spec,'ScaleOperator'))
                                  and not(contains(@spec,'BactrianScaleOperator'))
                                  and not(@spec='Uniform' and @tree)
                                  and not(@_b3spec='beast.base.evolution.operator.kernel.BactrianSubtreeSlide')]">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*[not(starts-with(name(),'_b3'))]"/>
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>

  <!-- Replace spec= attribute when _b3spec is present on the element -->
  <xsl:template match="@spec[parent::*/@_b3spec]">
    <xsl:attribute name="spec"><xsl:value-of select="../@_b3spec"/></xsl:attribute>
  </xsl:template>

  <!-- Replace type= attribute when _b3type is present on the element -->
  <xsl:template match="@type[parent::*/@_b3type]">
    <xsl:attribute name="type"><xsl:value-of select="../@_b3type"/></xsl:attribute>
  </xsl:template>

  <!-- Replace class= attribute when _b3class is present on the element -->
  <xsl:template match="@class[parent::*/@_b3class]">
    <xsl:attribute name="class"><xsl:value-of select="../@_b3class"/></xsl:attribute>
  </xsl:template>

  <!-- T4e — DeltaExchangeOperator: intparameter= → ivparameter=
       In BEAST3, IntSimplexParam is referenced via ivparameter= (not intparameter=).
       Also drop integer="true" which has no equivalent BEAST3 input. -->
  <xsl:template match="@intparameter[contains(../@spec,'DeltaExchangeOperator')]">
    <xsl:attribute name="ivparameter"><xsl:value-of select="."/></xsl:attribute>
  </xsl:template>
  <xsl:template match="@integer[contains(../@spec,'DeltaExchangeOperator')]"/>

  <!-- ═══════════════════════════════════════════════════════════════════
       T6 — Identity transform (base case)
       Copies all nodes and attributes not matched by a more specific rule.
       Internal _b3* attributes added by the Python pre-pass are stripped.
       ═══════════════════════════════════════════════════════════════════ -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

  <!-- Strip all _b3* helper attributes from output -->
  <xsl:template match="@*[starts-with(name(),'_b3')]"/>

</xsl:stylesheet>
