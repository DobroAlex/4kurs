\NeedsTeXFormat{LaTeX2e}[1998/12/01] % -*- coding: cp1251 -*-
\ProvidesClass{dynsys2014-11pt}[2014/02/02
  LaTeX class for preparing papers to Dynamic Systems]

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Based upon "LaTeX class for preparing papers to IX International Workshop on
% STABILITY AND OSCILLATIONS OF NONLINEAR CONTROL SYSTEMS (2006)",
%
% and kluwer.cls class file for the preparation of articles to be submitted to
% journals or spin-offs of journals published by Kluwer Academic Publishers.
%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Options
%
%\newif\ifukr\ukrfalse
%\DeclareOption{ukr}{\ukrtrue}
%\DeclareOption{rus}{\ukrfalse}
\ProcessOptions

\LoadClass[12pt,twoside,a4paper]{article}

\providecommand{\dynsysvolume}{?}
\providecommand{\dynsysvolpart}{?}
\providecommand{\dynsysissue}{3?}
\providecommand{\dynsysyear}{201?}
\providecommand{\dynsysnoblanks}{}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Required packages
%
\RequirePackage{ifthen}
\RequirePackage{amsmath}
\RequirePackage{amscd}
\RequirePackage{amsfonts}
\RequirePackage{amsthm}
\RequirePackage{euscript}
\RequirePackage{fancyhdr}
\RequirePackage{graphicx}
\RequirePackage{float}
%\RequirePackage{subfigure}
%\RequirePackage{floatflt} % purged from texlive
\RequirePackage{multicol}
%\RequirePackage{array}

\newcounter{firstpage}
\newcounter{absfirstpage}
%\newboolean{isfinal}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Page size
%
%\hoffset-1cm
\voffset=-1cm    
\textwidth=15.5cm
\textheight=23cm
\oddsidemargin=0.1cm
\evensidemargin=0cm

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Page style
%
\pagestyle{fancy}
\lhead{}
\chead{}
\rhead{}
\lfoot{}
\cfoot{}
\rfoot{}
\renewcommand{\headrulewidth}{0pt}
\renewcommand{\footrulewidth}{0pt}
\setlength{\topsep}{1pt}
\setlength{\partopsep}{0pt}
\addtolength{\headsep}{-10pt}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Localizations
%
\def\rulang{
  \def\myrefname{\CYRS\cyrp\cyri\cyrs\cyro\cyrk\space
      \cyrc\cyri\cyrt\cyri\cyrr\cyru\cyre\cyrm\cyrery\cyrh\space
      \cyri\cyrs\cyrt\cyro\cyrch\cyrn\cyri\cyrk\cyro\cyrv}  %% ������ ���������� ����������
  \def\definitionname{\CYRO\cyrp\cyrr\cyre\cyrd\cyre\cyrl\cyre\cyrn\cyri\cyre}   %%  �����������
  \def\theoremname{\CYRT\cyre\cyro\cyrr\cyre\cyrm\cyra}                          %%  �������
  \def\lemmaname{\CYRL\cyre\cyrm\cyrm\cyra}                                      %%  �����
  \def\corollaryname{\CYRS\cyrl\cyre\cyrd\cyrs\cyrt\cyrv\cyri\cyre}              %%  ���������
  \def\statementname{\CYRU\cyrt\cyrv\cyre\cyrr\cyrzh\cyrd\cyre\cyrn\cyri\cyre}   %%  �����������
  \def\proposalname{\CYRP\cyrr\cyre\cyrd\cyrl\cyro\cyrzh\cyre\cyrn\cyri\cyre}    %%  �����������
  \def\propositionname{\CYRP\cyrr\cyre\cyrd\cyrl\cyro\cyrzh\cyre\cyrn\cyri\cyre} %%  �����������
  \def\remarkname{\CYRZ\cyra\cyrm\cyre\cyrch\cyra\cyrn\cyri\cyre}                %%  ���������
  \def\algorithmname{\CYRA\cyrl\cyrg\cyro\cyrr\cyri\cyrt\cyrm}                   %%  ��������
  \def\problemname{\CYRZ\cyra\cyrd\cyra\cyrch\cyra}                              %%  ������
  \def\examplename{\CYRP\cyrr\cyri\cyrm\cyre\cyrr}                               %%  ������

  \renewcommand{\proofname}{\CYRD\cyro\cyrk\cyra\cyrz\cyra\cyrt\cyre\cyrl\cyrsftsn\cyrs\cyrt\cyrv\cyro} %% ��������������

  \def\myannotname{\CYRA\cyrn\cyrn\cyro\cyrt\cyra\cyrc\cyri\cyrya}                %% ���������
  \def\mykeywrdname{\CYRK\cyrl\cyryu\cyrch\cyre\cyrv\cyrery\cyre\space            %% �������� �����
                    \cyrs\cyrl\cyro\cyrv\cyra}

  \def\rulangname{\cyrr\cyru\cyrs\cyrs\cyrk\cyri\cyrishrt}           %% �������
  \def\uklangname{\cyrr\cyro\cyrs\cyrii\cyrishrt\cyrs\cyrsftsn\cyrk\cyra}         %% ���_�����
  \def\enlangname{Russian}
} % \rulang

\def\ukrlang{
  \def\myrefname{\CYRP\cyre\cyrr\cyre\cyrl\cyrii\cyrk\space
      \cyrc\cyri\cyrt\cyro\cyrv\cyra\cyrn\cyri\cyrh\space
      \cyrd\cyrzh\cyre\cyrr\cyre\cyrl}                               %%  ������ ��������� ������
  \def\definitionname{\CYRO\cyrz\cyrn\cyra\cyrch\cyre\cyrn\cyrn\cyrya}            %%  ���������
  \def\theoremname{\CYRT\cyre\cyro\cyrr\cyre\cyrm\cyra}                           %%  �������
  \def\lemmaname{\CYRL\cyre\cyrm\cyra}                                            %%  ����
  \def\corollaryname{\CYRN\cyra\cyrs\cyrl\cyrii\cyrd\cyro\cyrk}                   %%  �������
  \def\statementname{\CYRT\cyrv\cyre\cyrr\cyrd\cyrzh\cyre\cyrn\cyrn\cyrya}        %%  ����������
  \def\proposalname{\CYRP\cyrr\cyro\cyrp\cyro\cyrz\cyrii\cyrc\cyrii\cyrya}        %%  ���������
  \def\propositionname{\CYRP\cyrr\cyro\cyrp\cyro\cyrz\cyrii\cyrc\cyrii\cyrya}     %%  ���������
  \def\remarkname{\CYRZ\cyra\cyru\cyrv\cyra\cyrzh\cyre\cyrn\cyrn\cyrya}           %%  ����������
  \def\algorithmname{\CYRA\cyrl\cyrg\cyro\cyrr\cyri\cyrt\cyrm}                    %%  ��������
  \def\examplename{\CYRP\cyrr\cyri\cyrk\cyrl\cyra\cyrd}                           %%  �������

  \renewcommand{\proofname}{\CYRD\cyro\cyrv\cyre\cyrd\cyre\cyrn\cyrn\cyrya} %% ���������

  \def\myannotname{\CYRA\cyrn\cyro\cyrt\cyra\cyrc\cyrii\cyrya}                    %% ��������
  \def\mykeywrdname{\CYRK\cyrl\cyryu\cyrch\cyro\cyrv\cyrii\space                  %% ������ �����
                    \cyrs\cyrl\cyro\cyrv\cyra}

  \def\rulangname{\cyru\cyrk\cyrr\cyra\cyri\cyrn\cyrs\cyrk\cyri\cyrishrt}
  \def\uklangname{\cyru\cyrk\cyrr\cyra\cyryi\cyrn\cyrs\cyrsftsn\cyrk\cyra}
  \def\enlangname{Ukrainian}
} % \ukrlang


\def\enlang{
  \def\myrefname{References}
  \def\definitionname{Definition}
  \def\theoremname{Theorem}
  \def\lemmaname{Lemma}
  \def\corollaryname{Corollary}
  \def\statementname{Statement}
  \def\proposalname{Proposal}
  \def\propositionname{Proposition}
  \def\remarkname{Remark}
  \def\algorithmname{Algorithm}
  \def\problemname{Problem}
  \def\examplename{Example}

  \renewcommand{\proofname}{Proof}

  \def\myannotname{Abstracts}
  \def\mykeywrdname{Keywords}

  \def\rulangname{\cyra\cyrn\cyrg\cyrl\cyri\cyrishrt\cyrs\cyrk\cyri\cyrishrt}
  \def\uklangname{\cyra\cyrn\cyrg\cyrl\cyrii\cyrishrt\cyrs\cyrsftsn\cyrk\cyra}
  \def\enlangname{English}
} % \enlang

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Common localization strings
%

\def\rudynsysname{\CYRD\cyri\-\cyrn\cyra\-\cyrm\cyri\-\cyrch\cyre\-\cyrs\cyrk\cyri\cyre\thickspace
  \cyrs\cyri\-\cyrs\cyrt\cyre\-\cyrm\cyrery}
\def\ukdynsysname{\rudynsysname}
\def\endynsysname{Dinamicheskie Sistemy}

\def\ruissuename{\CYRT\cyro\cyrm}
\def\ukissuename{\CYRT\cyro\cyrm}

\def\rukeywrdname{\CYRK\cyrl\cyryu\cyrch\cyre\cyrv\cyrery\cyre\thickspace
  \cyrs\cyrl\cyro\cyrv\cyra}
\def\ukkeywrdname{\CYRK\cyrl\cyryu\cyrch\cyro\cyrv\cyrii\thickspace
  \cyrs\cyrl\cyro\cyrv\cyra}
\def\enkeywrdname{Keywords}

\def\rufiguresname{\CYRI\cyrl.}
\def\ukfiguresname{\CYRII\cyrl.}

\def\rutablesname{\CYRT\cyra\cyrb\cyrl.}
\def\uktablesname{\CYRT\cyra\cyrb\cyrl.}

\def\ukbiblname{\CYRB\cyrii\cyrb\cyrl\cyrii\cyro\cyrg\cyrr.}
\def\uknazvname{\cyrn\cyra\cyrz\cyrv}

\def\rubiblname{\CYRB\cyri\cyrb\cyrl\cyri\cyro\cyrg\cyrr.}
\def\runazvname{\cyrn\cyra\cyrz\cyrv}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Environments
%
%\newenvironment{annot}
%  {\begin{small}{\bf ANNOT.}}
%  {\end{small}}
%\newenvironment{keywrd}
%  {\begin{small}{\bf KEYWRD:}}
%  {\end{small}}

%
% Theorem
%
\theoremstyle{definition}
\newtheorem{definition}{\definitionname}   %%  �����������
\newtheorem{problem}{\problemname}         %%  ������
\newtheorem{proposition}{\propositionname} %%  �����������
\theoremstyle{plain}
\newtheorem{theorem}{\theoremname}         %%  �������
\newtheorem{lemma}{\lemmaname}             %%  �����
\newtheorem{corollary}{\corollaryname}     %%  ���������
\newtheorem{statement}{\statementname}     %%  �����������
\newtheorem{proposal}{\proposalname}       %%  �����������
\newtheorem{algorithm}{\algorithmname}     %%  ��������
\theoremstyle{remark}
\newtheorem{remark}{\remarkname}           %%  ���������
\newtheorem{example}{\examplename}         %%  ������

\newcommand{\dropcounters}{
  \setcounter{definition}{0}
  \setcounter{problem}{0}
  \setcounter{proposition}{0}
  \setcounter{theorem}{0}
  \setcounter{lemma}{0}
  \setcounter{corollary}{0}
  \setcounter{statement}{0}
  \setcounter{proposal}{0}
  \setcounter{algorithm}{0}
  \setcounter{remark}{0}
  \setcounter{example}{0}}

\theoremstyle{definition}
\newtheorem*{definition*}{\definitionname}   %%  �����������
\newtheorem*{problem*}{\problemname}         %%  ������
\newtheorem*{proposition*}{\propositionname} %%  �����������
\theoremstyle{plain}
\newtheorem*{theorem*}{\theoremname}         %%  �������
\newtheorem*{lemma*}{\lemmaname}             %%  �����
\newtheorem*{corollary*}{\corollaryname}     %%  ���������
\newtheorem*{statement*}{\statementname}     %%  �����������
\newtheorem*{proposal*}{\proposalname}       %%  �����������
\newtheorem*{algorithm*}{\algorithmname}     %%  ��������
\theoremstyle{remark}
\newtheorem*{remark*}{\remarkname}           %%  ���������
\newtheorem*{example*}{\examplename}         %%  ������

%
% choose language
%
%\ifukr
%  \ukrlang
%\else
%  \rulang
%\fi

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% \captioncontent{fltnum}{caption}
%
\def\captioncontent#1#2{{\footnotesize #1. #2}}

%\belowcaptionskip=0pt

\renewcommand{\@makecaption}[2]{
  \vspace{\abovecaptionskip}
  \sbox{\@tempboxa}{\captioncontent{#1}{#2}}
  \ifdim \wd\@tempboxa >\hsize
    \captioncontent{#1}{#2}
  \else
    \global\@minipagefalse
    \hbox to \hsize {\hfil \captioncontent{#1}{#2} \hfil}
  \fi
  \vspace{\belowcaptionskip}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Equation numbering
%
\newif\ifsinglenum\singlenumfalse

\def\singlenumbering{
  \def\theequation{\arabic{equation}}
  \singlenumtrue}
\def\singlesection{\singlenumbering}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Extra blank page
%
\newif\ifextrablankpage\extrablankpagetrue

\def\noextrablankpage{\extrablankpagefalse}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Automatic article-language abstracts
%
\newif\ifautoabstracts\autoabstractstrue

\def\noautoabstracts{\autoabstractsfalse}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Title page
%

\def\showpagesinfo{1}
\def\showcopyright{1}

%
% \setupheaders
%
\def\setupheaders{
  \lhead{\footnotesize
         \ifthenelse{\equal{\showpagesinfo}{}
	             \or
		     \not\equal{\value{page}}{\value{firstpage}}}
	   {\theheadings}
	   {\sl \CYRM\CYRI\CYRK\CYRM\CYRO\space-\space2019
  }}
  \cfoot{\footnotesize
         \ifthenelse{\equal{\showpagesinfo}{}
	             \or
	             \not\equal{\value{page}}{\value{firstpage}}}
	   {\sl \CYRT\cyra\cyrv\cyrr\cyri\cyrch\cyre\cyrs\cyrk\cyra\cyrya\space\cyrn\cyra\cyru\cyrch\cyrn\cyra\cyrya\space\cyrsh\cyrk\cyro\cyrl\cyra-\cyrk\cyro\cyrn\cyrf\cyre\cyrr\cyre\cyrn\cyrc\cyri\cyrya\space\cyrs\cyrt\cyru\cyrd\cyre\cyrn\cyrt\cyro\cyrv\space\cyri\space\cyrm\cyro\cyrl\cyro\cyrd\text{�}\cyrh\space\cyrs\cyrp\cyre\cyrc\cyri\cyra\cyrl\cyri\cyrs\cyrt\cyro\cyrv\space-\space2019}
	   {}
  }}

\def\printlogo{
  \begin{flushright}
%   \vskip-5mm
   \small\textsf{
        \CYRD\CYRI\CYRN\CYRA\CYRM\CYRI\CYRCH\CYRE\CYRS\CYRK\CYRI\CYRE\space
           \CYRS\CYRI\CYRS\CYRT\CYRE\CYRM\CYRERY\\
%    ������������ �������
    \CYRM\cyre\cyrzh\cyrv\cyre\cyrd\cyro\cyrm\cyrs\cyrt\cyrv\cyre\cyrn\cyrn\cyrery\cyrishrt\space
    \cyrn\cyra\cyru\cyrch\cyrn\cyrery\cyrishrt\space
    \cyrs\cyrb\cyro\cyrr\cyrn\cyri\cyrk\\}
%    ���������������� ������� �������\\
%    \tt
%    \CYRI\cyrz\cyrd\cyra\cyre\cyrt\cyrs\cyrya\space \cyrs\space 1982 \cyrg\cyro\cyrd\cyra\\
%%    �������� � 1982 ����\\
%    \CYRT\CYRN\CYRU\space
%%    ��� 
%%    ����������� ������������ �����������
%    \cyri\cyrm.\CYRV.\CYRI.\CYRV\cyre\cyrr\cyrn\cyra\cyrd\cyrs\cyrk\cyro\cyrg\cyro
%%    ��.�.�.�����������
  \end{flushright}}

%
% \udc{index}
%
\newcommand{\udc}[1]{\def\udcvalue{#1}\def\mscvalue{}}

\def\printudc{
  \begin{flushleft}
    \ifthenelse{\equal{\mscvalue}{}}
               {\CYRU\CYRD\CYRK\space \udcvalue}
               {MSC\space 2010:\space \mscvalue}
    \vskip-1mm
  \end{flushleft}}

%
% \msc{MSC-2010}
%
\providecommand{\mscvalue}{}
\newcommand{\msc}[1]{\def\mscvalue{#1}}

%
% \setheadings{odd}{even}
%
\newcommand{\setheadings}[2]{
  \gdef\theheadings{\ifodd\value{page} #1 \thepage \else \thepage #2 \fi}}

%
% \setbothheadings{headings}
%
\def\setbothheadings#1{\setheadings{#1}{#1}}

%
% \topmatter{authors}{shorttitle}
%
\newcommand{\topmatter}[1]{
  \def\dateofarrival{}
  \def\dateofrevision{}
  \def\dateofacception{}

  \def\theequation{\arabic{section}.\arabic{equation}}
  \singlenumfalse
  \ifthenelse{\equal{\dynsysnoblanks}{}}{\extrablankpagetrue}{\extrablankpagefalse}
  \autoabstractstrue

  \def\defsetheadings{
    \setheadings{\qquad\qquad \textsl{����������\,�����������\,������������\,�����\,�������������\,�����������} \hfill}
                {\space\space \textsl{����������\,�����������\,������������\,�����\,�������������\,�����������} \hfill}}
  

  \lfoot{
    \ifthenelse{\equal{\showcopyright}{}}
	       {}
	       {\footnotesize
		\ifnum\value{page}=\value{firstpage}
		  \small \raisebox{1pt}{\copyright}
		  \textsl{\MakeUppercase{#1}}
		\else
		  % empty
		\fi}}
  \ifthenelse{\equal{\showcopyright}{}}
	     {\def\articleauthors{}}
	     {\def\articleauthors{#1}}}

%
% \title{title}
% \thanks{text}
%
\renewcommand{\title}[1]{\def\articletitle{#1}\def\articlenote{}}
\renewcommand{\thanks}[1]{\def\articlenote{#1}}

\def\printtitle{
  \begin{flushleft}
    %\lineskip 10pt
    {\Large \textbf{\articletitle}\ifthenelse{\equal{\articlenote}{}}{}{\footnote{\articlenote}}\\ }
  \end{flushleft}
  \vskip1mm
  }

%
% \author{authors}{university}
%
\renewcommand{\author}[2]{
  \def\articletitauthors{#1}
  \def\authorsuniversity{#2}}

\def\printauthors{
  \ifthenelse{\equal{\articletitauthors}{}}{}{
  \begin{flushleft}
    {\large\bf \articletitauthors} \\
    %\smallskip
    {\small \authorsuniversity}
  \end{flushleft}
  %\vskip3mm
  }}

%
% \maketitle
%
\renewcommand{\maketitle}{
  \setupheaders

%  \printlogo
%  \bigskip

  \printudc
  \printtitle
  \printauthors

  \ifthenelse{\equal{\articletitauthors}{}}
	     {\addarticletoc{\noindent \articletitle}}
	     {\addarticletoc{\noindent \MakeUppercase{\articletitauthors}.
		 \articletitle}}
  }

%
% \cleararticle
%
\newcommand{\cleararticle}{
  \udc{}
  \title{}
  \author{}{}
  \date{}
  \revisiondate{}
  \acceptiondate{}
  \def\articleabstracts{}
  \def\showpagesinfo{}
  \def\showcopyright{}}

%
% \articlehead[language]
%
\newcommand{\articlehead}[1][rus]{
  \defsetheadings
  \ifthenelse{\equal{#1}{ukr}}{\ukrlang}{\ifthenelse{\equal{#1}{eng}}{\enlang}{\rulang}}
  \def\articlelang{#1}
  \setcounter{firstpage}{\value{page}}
  \dropcounters
  \label{thefirstpage}
  \maketitle}

\newenvironment{article}
  {\ifodd\value{page} % do nothing
   \else
     \ifextrablankpage
       \quad
       \setbothheadings{\hfill}
       \lhead{}
       \cfoot{}
       \newpage
     \else \fi
   \fi
   \articlehead}
  {
   \nopagebreak[4]
   \medskip
   {\printdates}
   \label{thelastpage}
   \storeruabstracts{\articleauthorsrus}{\articletitlerus}{\articleabstractsrus}{\articlekeywordsrus}
   \ifthenelse{\not\isundefined{\articletitleukr}}{
     \storeukabstracts{\articleauthorsukr}{\articletitleukr}{\articleabstractsukr}{\articlekeywordsukr}
   }{}
   \storeenabstracts{\mscvalueeng}{\articleauthorseng}{\articletitleeng}{\articleabstractseng}{\articlekeywordseng}
  }
%   \ifthenelse{\equal{\articleabstracts}{}}{}{\nlabstracts}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Bottom matter
%

%
% \tocpagenumwidth
%
\def\tocpagenumwidth{0.75cm}

%
% \addarticletoc{text} % internal
% 
\newcommand{\addarticletoc}[1]{
  \addtocontents{toc}
    {\protect
     \small\rm
     {\rightskip=3\parindent #1\par}
     \vskip-\baselineskip \hfill \pageref{thefirstpage}
     \par\medskip}}

%      \leaders\hbox to 1em{\hss.\hss}
%      \hfill \thepage}}}

\newcommand{\addarticleentoc}[1]{
  \addtocontents{entoc}
    {\protect
     \small\rm
     {\rightskip=3\parindent #1\par}
     \vskip-\baselineskip \hfill \pageref{thefirstpage}
     \par\medskip}}


%
% \date{date-of-arrival}
%
\renewcommand{\date}[1]{\def\dateofarrival{#1}}

%
% \revisiondate{date-of-revision}
%
\newcommand{\revisiondate}[1]{\def\dateofrevision{#1}}

%
% \acceptiondate{date-of-acception}
%
\newcommand{\acceptiondate}[1]{\def\dateofacception{#1}}

\def\printdates{
  \begin{flushright}
    \footnotesize
    \ifthenelse{\equal{\dateofarrival}{}}
	       {}
	       {\textit{\CYRP\cyro\cyrl\cyru\cyrch\cyre\cyrn\cyr�\space
		   \dateofarrival}} % ��������
    \ifthenelse{\equal{\dateofrevision}{}}
	       {}
	       {\quad
		\textit{\CYRP\cyre\cyrr\cyre\cyrr\cyra\cyrb\cyro\cyrt\cyra\cyrn\cyra\space
		  \dateofrevision}} % ������������
    \ifthenelse{\equal{\dateofacception}{}}
	       {}
	       {\par
		\textit{\CYRP\cyrr\cyri\cyrn\cyrya\cyrt\cyra\space
		  \cyrk\space
		  \cyrp\cyru\cyrb\cyrl\cyri\cyrk\cyra\cyrc\cyri\cyri\space
		  \dateofacception}} % ������� � ����������
  \end{flushright}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Abstracts
%

\providecommand{\listofruabstracts}{\@starttoc{rua}}
%
% \ruabstracts{authors}{title}{content}
%
\newcommand{\ruabstracts}[4]{
  \def\articleauthorsrus{#1}
  \def\articletitlerus{#2}
  \def\articleabstractsrus{#3}
  \def\articlekeywordsrus{#4}}

\newcommand{\storeruabstracts}[4]{
  \addtocontents{sitetoc}{\protect\footnotesize \MakeUppercase{#1}.\space {#2}}
  \addtocontents{rua}
    {\protect\footnotesize
     \protect\bigskip
     \protect\par\noindent \CYRU\CYRD\CYRK~\udcvalue
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par\noindent
     \MakeUppercase{#1}.\space \textbf{#2} (\rulangname)~//
     \rudynsysname, \dynsysyear.~---
     \ruissuename~\dynsysvolume(\dynsysissue),~\textnumero\dynsysvolpart.~---
     \CYRS.~\pageref{thefirstpage}--\pageref{thelastpage}.
% ������������ �������: ������. ����. ��.~--- ���, 2006.~--- ���.1(29),~No.1.~--- �.~\pageref{thefirstpage}--\pageref{thelastpage}.}
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par
     #3
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par
     \protect\noindent
     {\bf \rukeywrdname:} #4
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par
     \protect\noindent
     \ifnum\value{figure}=0 %empty
     \else \rufiguresname~\arabic{figure}. \fi
     \ifnum\value{table}=0 %empty
     \else \rutablesname~\arabic{table}. \fi
     \rubiblname~\arabic{bibcounter} \runazvname.
% ��.~N1. ����.~N2. ��������.~N ����.
}}

\providecommand{\listofukabstracts}{\@starttoc{uka}}
%
% \ukabstracts{authors}{title}{content}
%
\newcommand{\ukabstracts}[4]{
  \def\articleauthorsukr{#1}
  \def\articletitleukr{#2}
  \def\articleabstractsukr{#3}
  \def\articlekeywordsukr{#4}}

\newcommand{\storeukabstracts}[4]{
  \addtocontents{uka}
    {\protect\footnotesize
     \protect\bigskip
     \protect\par\noindent \CYRU\CYRD\CYRK~\udcvalue
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par\noindent
     \MakeUppercase{#1}.\space \textbf{#2} (\uklangname)~//
     \ukdynsysname, \dynsysyear.~---
     \ukissuename~\dynsysvolume(\dynsysissue),~\textnumero\dynsysvolpart.~---
     \CYRS.~\pageref{thefirstpage}--\pageref{thelastpage}.
% ������������ �������: ������. ����. ��.~--- ���, 2006.~--- ���.~20.~--- �.~\pageref{thefirstpage}--\pageref{thelastpage}.}
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par
     #3
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par
     \protect\noindent
     {\bf \ukkeywrdname:} #4
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par
     \protect\noindent
     \ifnum\value{figure}=0 %empty
     \else \ukfiguresname~\arabic{figure}. \fi
     \ifnum\value{table}=0 %empty
     \else \uktablesname~\arabic{table}. \fi
     \ukbiblname~\arabic{bibcounter} \uknazvname.
% ��.~N1. ����.~N2. ��������.~N ����.
}}

\providecommand{\listofenabstracts}{\@starttoc{ena}}
%
% \enabstracts{MSC}{authors}{title}{content}
%
\newcommand{\enabstracts}[6]{
  \def\mscvalueeng{#1}
  \def\articleauthorseng{#2}
  \def\authorsuniversityeng{#3}
  \def\articletitleeng{#4}
  \def\articleabstractseng{#5}
  \def\articlekeywordseng{#6}}

\newcommand{\storeenabstracts}[5]{
  \addtocontents{ena}
    {\protect\footnotesize
     \protect\bigskip
     \protect\par
     \protect\foreignlanguage{english}{\protect\noindent MSC 2010: #1}
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par
     \protect\foreignlanguage{english}{
      \protect\noindent \MakeUppercase{#2}.\space \textbf{#3} (\enlangname).
      \endynsysname~\textbf{\dynsysvolume(\dynsysissue)},~no.\dynsysvolpart,
      \pageref{thefirstpage}--\pageref{thelastpage} (\dynsysyear).}
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par\protect\foreignlanguage{english}{#4}
     \protect\nopagebreak[4]
     \protect\smallskip
     \protect\par\protect\foreignlanguage{english}{\protect\noindent {\bf \enkeywrdname:} #5}
     \protect\nopagebreak[4]
     \protect\medskip
     \protect\par
     \protect\foreignlanguage{english}{
      \protect\noindent
      \ifnum\value{figure}=0 %empty
      \else Fig.~\arabic{figure}. \fi
      \ifnum\value{table}=0 %empty
      \else Tbl.~\arabic{table}. \fi
      Ref.~\arabic{bibcounter}.}}
    \addarticleentoc{\noindent \MakeUppercase{#2}. #3}
    }
      % Fig.~N1. Tbl.~N2. Ref.~N.

% \providecommand{\listofnlabstracts}{\@starttoc{nla}}
% %
% % \nlabstracts
% %
% \newcommand{\nlabstracts}{
%   \addtocontents{nla}
%     {\protect\footnotesize
%      \protect\bigskip
%      \protect\par\noindent \CYRU\CYRD\CYRK~\udcvalue
%      \protect\nopagebreak[4]
%      \protect\smallskip
%      \protect\par\noindent
%      \MakeUppercase{\articletitauthors}.\space \textbf{\articletitle}
%      (\nllangname)~//
%      \nldynsysname, \dynsysyear.~---
%      \nlissuename~\dynsysissue.~---
%      \CYRS.~\pageref{thefirstpage}--\pageref{thelastpage}.
%      \protect\nopagebreak[4]
%      \protect\medskip
%      \protect\par
%      \articleabstracts
%      \protect\nopagebreak[4]
%      \protect\smallskip
%      \protect\par
%      \protect\noindent
%      {\bf \nlkeywrdname:} \articlekeywords
%      \protect\nopagebreak[4]
%      \protect\medskip
%      \protect\par
%      \protect\noindent
%      \ifnum\value{figure}=0 %empty
%      \else \nlfiguresname~\arabic{figure}. \fi
%      \ifnum\value{table}=0 %empty
%      \else \nltablesname~\arabic{table}. \fi
%      \nlbiblname~\arabic{bibcounter} \nlnazvname.
% % ��.~N1. ����.~N2. �������.~N ����.
% }}

%\def\annotmargin{1.0cm}

%
% \makeenglishtitle
%
\newcommand{\makeenglishtitle}{
  \vskip2mm
  \begin{flushleft}
    \noindent\textbf{\large \articletitleeng}
    \smallskip

    \noindent\textbf{\articleauthorseng}

    {\noindent\small \authorsuniversityeng.}
  \end{flushleft}}

%
% \makeenglishabstracts
%
\newcommand{\makeenglishabstracts}{
  \begin{footnotesize}
  	\medskip
    \noindent
    \textbf{Abstract.} \articleabstractseng

    \noindent
    \textbf{Keywords:} \articlekeywordseng

    \noindent
    \textbf{MSC 2010:} \mscvalueeng
  \end{footnotesize}}

%
% \makeenglishhead
%
\newcommand{\makeenglishhead}{
  
  \makeenglishtitle
  \makeenglishabstracts
}

%
% \abstracts{Abstracts text.}
%
\newcommand{\abstracts}[2]{
  \def\articleabstracts{#1}
  \def\articlekeywords{#2}
  \ifautoabstracts
    \ifthenelse{\equal{\articlelang}{ukr}}
      {\ukabstracts{\articleauthors}{\articletitle}{\articleabstracts}{\articlekeywords}}
      {\ifthenelse{\equal{\articlelang}{eng}}
        {\enabstracts{\mscvalue}{\articleauthors}{\authorsuniversity}{\articletitle}{\articleabstracts}{\articlekeywords}}
        {\ruabstracts{\articleauthors}{\articletitle}{\articleabstracts}{\articlekeywords}}}
  \else \fi
  
  \begin{footnotesize}
    \medskip
    \noindent
    {\bf \myannotname.} \articleabstracts
    \par\noindent
    {\bf \mykeywrdname:} \articlekeywords
  \end{footnotesize}

  \ifthenelse{\not{\equal{\articlelang}{eng}}}
  {\makeenglishhead}
  {}
}

%\newcommand{\annot}[1]{
%  \begin{list}{}{\setlength{\leftmargin}{\annotmargin}
%                 \setlength{\rightmargin}{\annotmargin}}
%     \item[] #1
%  \end{list}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% The following is borrowed from kluwer.cls as of 2000/04/29.
% The marcos below are sligthly modified.
%
\newcommand{\vsecspace}[1]{%
   \if@nobreak\else
     \vskip #1\relax \fi}

%%%%%%%%%%%
% section

\def\Shaveatleast{5\baselineskip}
\def\SShaveatleast{4\baselineskip}
\def\SSShaveatleast{4\baselineskip}
\newcommand{\@haveatleast}[1]{\relax\par \vskip #1
     \penalty 0\vskip -#1 \relax }

\newcommand{\Forcedhang}[1]{\leavevmode
    \setbox0=\hbox{#1}\hangindent\wd0
    \hangafter=1 \box0 }
\newcommand{\Shang}[1]{\Forcedhang{#1}}
\newcommand{\Sflushstyle}{\raggedright}
%\newcommand{\Snumstyle}{\normalfont\Large\bf}
%\newcommand{\Stextstyle}{\normalfont\Large\bf}
\newcommand{\Sspaceabove}{1\baselineskip plus6pt minus4pt}
\newcommand{\Sspaceafter}{\vskip 1\baselineskip plus3pt minus2pt}
%\newcommand{\Scase}[1]{#1}
\newcommand{\sectiondot}{.}

\newcommand{\@makesectionhead}[2]{\vsecspace{\Sspaceabove}%
  {\parindent0pt \hyphenpenalty=10000 \baselineskip12pt
   \Sflushstyle {\normalfont\large\bf #1}\par
   \nobreak \Sspaceafter \relax
   }}

\def\section{\@haveatleast{\Shaveatleast
    }\@afterindentfalse \secdef\@section\@ssection}

\def\@section[#1]#2{%
  \ifsinglenum \else \setcounter{equation}{0} \fi
  \ifnum \c@secnumdepth >\z@
    \refstepcounter{section}%
%    \addcontentsline{toc}{section}{\protect \numberline{\thesection}#1}%
  \else
%    \addcontentsline{toc}{section}{#1}%
  \fi
  \sectionmark{#1}%
  \@makesectionhead{\thesection\sectiondot~#2}%
  \@afterheading
  }

\def\@ssection#1{\@makesectionhead{#1}\@afterheading}

%%%%%%%%%%%%%%
% subsection

\newcommand{\SShang}[1]{\Forcedhang{#1}}
\newcommand{\SSflushstyle}{\raggedright}
%\newcommand{\SSnumstyle}{\normalsize\rm}
%\newcommand{\SStextstyle}{\sc}
\newcommand{\SSspaceabove}{1\baselineskip plus3pt minus2pt}
\newcommand{\SSspaceafter}{\vskip .8\baselineskip plus2pt minus2pt}
%\newcommand{\SScase}[1]{#1}
\newcommand{\@makesubsectionhead}[2]{\vsecspace{\SSspaceabove}%
  {\parindent0pt \hyphenpenalty=10000 \baselineskip12pt
   \SSflushstyle {\normalfont\normalsize\bf #1}\par
   \nobreak \SSspaceafter \relax
   }}

\def\subsection{\@haveatleast{\SShaveatleast}%
  \@afterindentfalse \secdef\@subsection\@ssubsection
  }

\def\@subsection[#1]#2{%
  \ifnum \c@secnumdepth >\@ne
    \refstepcounter{subsection}%
%    \addcontentsline{toc}{subsection}{\protect \numberline{\thesubsection}#1}%
  \else
%    \addcontentsline{toc}{subsection}{#1}%
  \fi
  \subsectionmark{#1}
  \@makesubsectionhead{\thesubsection\sectiondot~#2}%
  \@afterheading
  }
\def\@ssubsection#1{\@makesubsectionhead{#1}\@afterheading}

%%%%%%%%%%%%%%%%%
% subsubsection

\newcommand{\SSShang}[1]{\Forcedhang{#1}}
\newcommand{\SSSflushstyle}{\raggedright}
%\newcommand{\SSSnumstyle}{\normalsize\rm}
%\newcommand{\SSStextstyle}{\sc}
\newcommand{\SSSspaceabove}{1\baselineskip plus3pt minus2pt}
\newcommand{\SSSspaceafter}{\vskip .6\baselineskip plus2pt minus2pt}
%\newcommand{\SSScase}[1]{#1}
\newcommand{\@makesubsubsectionhead}[2]{\vsecspace{\SSSspaceabove}%
  {\parindent0pt \hyphenpenalty=10000 \baselineskip12pt
   \SSSflushstyle {\normalfont\normalsize\bf #1}\par
   \nobreak \SSSspaceafter \relax
   }}

\def\subsubsection{\@haveatleast{\SSShaveatleast}%
  \@afterindentfalse \secdef\@subsubsection\@ssubsubsection
  }

\def\@subsubsection[#1]#2{%
  \ifnum \c@secnumdepth >\@ne
    \refstepcounter{subsubsection}%
%    \addcontentsline{toc}{subsection}{\protect \numberline{\thesubsection}#1}%
  \else
%    \addcontentsline{toc}{subsection}{#1}%
  \fi
  \subsubsectionmark{#1}
  \@makesubsubsectionhead{\thesubsubsection\sectiondot~#2}%
  \@afterheading
  }
\def\@ssubsubsection#1{\@makesubsubsectionhead{#1}\@afterheading}

%
% end of kluwer.cls borrowings
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% A hack to get references title centered and avoid use of `markboth'.
%
\newcommand{\centersect}[1]{
  \bigskip
  \centerline{\large\bf #1}}

\newcommand\Nopagebreak{
  \makeatletter
  \@nobreaktrue\nopagebreak
  \makeatother}

\newcounter{bibcounter}
\renewenvironment{thebibliography}[1]
     {\centersect{\refname}
      \Nopagebreak %[4]
      \medskip
%%      \@mkboth{\MakeUppercase\refname}{\MakeUppercase\refname}%
      \list{\@biblabel{\@arabic\value{bibcounter}}} %\c@enumiv}}%
           {\settowidth\labelwidth{\@biblabel{#1}}%
            \leftmargin\labelwidth
            \advance\leftmargin\labelsep
            \@openbib@code
            \usecounter{bibcounter} %enumiv}%
            %\let\p@enumiv\@empty
            \let\p@bibcounter\@empty
            %\renewcommand\theenumiv{\@arabic\c@enumiv}}%
            \renewcommand\thebibcounter{\@arabic\value{bibcounter}}}%
      \sloppy
      \clubpenalty4000
      \@clubpenalty \clubpenalty
      \widowpenalty4000%
      \sfcode`\.\@m
      \def\refitem##1##2{\bibitem{##1} {\it ##2}\/}}
     {\def\@noitemerr
       {\@latex@warning{Empty `thebibliography' environment}}%
      \endlist}

\def\bibname{\normalsize\bf \myrefname}
\def\refname{\normalsize\bf \myrefname}
\def\@biblabel#1{#1.\hfill}
\newenvironment{references}
  {\def\refname{\normalsize\bf \myrefname}
   \small
   \begin{thebibliography}{99}
     \setlength{\itemsep}{-2.5pt}}
  {\end{thebibliography}}
%
% end of bibliography hack
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\endinput
