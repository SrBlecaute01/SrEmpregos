# SrEmpregos

[![GitHub stars](https://img.shields.io/github/stars/SrBlecaute01/SrEmpregos.svg)](https://github.com/SrBlecaute01/SrEmpregos/stargazers)
[![GitHub All Releases](https://img.shields.io/github/downloads/SrBlecaute01/SrEmpregos/total.svg?logoColor=fff)](https://github.com/SrBlecaute01/SrEmpregos/releases/latest)
[![GitHub issues](https://img.shields.io/github/issues-raw/SrBlecaute01/SrEmpregos.svg?label=issues)](https://github.com/SrBlecaute01/SrEmpregos/issues)
[![GitHub last commit](https://img.shields.io/github/last-commit/SrBlecaute01/SrEmpregos.svg)](https://github.com/SrBlecaute01/SrEmpregos/commit)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/48103a1b283946fa8863d2c6d964375e)](https://www.codacy.com/manual/SrBlecaute01/SrEmpregos?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SrBlecaute01/SrEmpregos&amp;utm_campaign=Badge_Grade)

  Plugin de empregos com suporte a Legendchat, PlaceholderAPI e MVdWPlaceholderAPI. Atualmente existem 7 tipos de empregos que tem suas funções configuráveis: Minerador, Escavador, Lenhador, Caçador, Assassino, Pescador e Fazendeiro. 
  O sistema conta com um sistema de salário toda vez que o jogador conseguir bater a meta estipulada. Além disso, existe um sistema de quests que pode aumentar o salário do jogador.

![Preview](https://github.com/SrBlecaute01/SrEmpregos/raw/master/assets/Screenshot_2.png)

### Dependências
  
  O plugin necessita que tenha o Vault instalado em seu servidor e algum plugin de economia. 

### Comandos

- `/empregos` - Visualiza o menu de empregos

### Placeholders

| Placeholder| Descrição |
|--|--|
| {jobs_player_job} | nome do emprego do jogador |
| {jobs_player_meta} |  total de ações efetuadas (quebrar, cortar, pescar etc) |
| {jobs_player_current} | total de ações efetuadas para receber o salário |
| {jobs_player_quests} | quantidade de quests concluidas |
| {jobs_player_salary} | salário atual do jogador |
| {jobs_player_progress} |  barra de progresso para receber o salário |

### Empregos

| Emprego| Permissao|
|--|--|
| Escavador  | job.digger |
| Fazendeiro | job.farmer |
| Pescador   | job.fisher |
| Caçador    | job.hunter |
| Assassino  | job.killer |
| Lenhador   | job.lumberjack|
| minerador  | job.miner |

### Config

```yaml
#   _________  ____  _       ___    __  ____ __ __ ______   ___
#  / ___/    \|    \| |     /  _]  /  ]/    |  |  |      | /  _]
# (   \_|  D  )  o  ) |    /  [_  /  /|  o  |  |  |      |/  [_
#  \__  |    /|     | |___|    _]/  / |     |  |  |_|  |_|    _]
#  /  \ |    \|  O  |     |   [_/   \_|  _  |  :  | |  | |   [_
#  \    |  .  \     |     |     \     |  |  |     | |  | |     |
#   \___|__|\_|_____|_____|_____|\____|__|__|\__,_| |__| |_____|
#           plugin de empregos    Versão: 1.3.1

# caso desativado irá usar sqlite como forma de armazenamento.
MySQL:
  ativado: false
  host: 'localhost'
  porta: '3306'
  database: ''
  usuario: ''
  senha: ''

# ==== [Permissões para cada emprego]
#
# escavador    : job.digger
# fazendeiro   : job.farmer
# pescador     : job.fisher
# caçador      : job.hunter
# assassino    : job.killer
# lenhador     : job.lumberjack
# minerador    : job.miner
#
# =====

# =================== [Placeholders]
#
#   {jobs_player_job}        : nome do emprego do jogador
#   {jobs_player_meta}       : total de ações efetuadas (quebrar, cortar, pescar etc)
#   {jobs_player_current}    : total de ações efetuadas para receber o salário
#   {jobs_player_quests}     : quantidade de quests concluidas
#   {jobs_player_salary}     : salário atual do jogador
#   {jobs_player_progress}   : progressonpara receber o salário
#
# OBS: caso use PlaceholderAPi use % no lugar dos {}
#
# ==============

# Sons do sistema, mais sons em -> https://github.com/Attano/Spigot-1.8/blob/master/org/bukkit/Sound.java
sons:
  erro: 'NOTE_BASS'
  geral: 'ITEM_PICKUP'
  sucesso: 'NOTE_PLING'

# mensagens do plugin
mensagens:
  sem-permissao: '&cVocê não tem permissão para isso!'
  sem-emprego: '&cVocê não tem nenhum emprego para poder fazer isso!'
  tem-emprego: '&cVocê já tem um emprego! peça demissão para exercer este cargo'
  contratado: '&aVocê foi contratado para o emprego de @job.'
  pediu-demissao: '&cVocê pediu demissão do seu emprego!'

  pagamento:
  - ' '
  - ' &eEmprego: &7você recebeu seu salário de &2R$ &f@salary'
  - ' '

# tag do emprego no legendchat {empregos}
tag: true

# valores que aparecerão nos gui e nas placeholders
placeholders:
  desempregado: '&cSem emprego'
  selecionar-emorego: '&aClique para selecionar esse emprego'
  emprego-selecionado: '&cVocê já está nesse emprego'
  progresso:
    # quantidade de barras
    quantidade: 10
    # cor barra completa
    cor-completa: '&a'
    # cor barra incompleta
    cor-incompleta: '&7'
    # cor da porcentagem
    cor-porcentagem: '&a'
    # simbolo da barra de progresso
    simbolo: '|'


# inventários do plugin
inventarios:
  desafios: '&8Seus desafios!'
  demissao: '&8Pedir demissão ?'
  empregos:
    nome: '&8Empregos'
    linhas: 5

  item-conquistas:
    slot: 37
    skull:
      ativado: false
      url: ''
    material: 'NETHER_STAR'
    data: 0
    nome: '&aSuas conquistas'
    lore:
      - '&7Veja suas conquistas'


  #   @job     : nome do emprego
  #   @tag     : tag do emprego
  #   @meta    : meta para receer o salário
  #   @salary  : salário do emprego
  #   @quests  : número de quests
  minerador:
    slot: 10
    # se ativado ele irá ignorar o item
    skull:
      ativado: false
      url: ''
    # material
    material: 'DIAMOND_PICKAXE'
    # data do material
    data: 0
    # nome do item
    nome: '&eMinerador'
    # lore do item
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &cminerar @meta blocos'
      - '&7Quests: &e@quests'
      - ''

  escavador:
    slot: 14
    skull:
      ativado: false
      url: ''
    material: 'DIAMOND_SPADE'
    data: 0
    nome: '&eEscavador'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &cescavar @meta blocos'
      - '&7Quests: &e@quests'
      - ''

  lenhador:
    slot: 12
    skull:
      ativado: false
      url: ''
    material: 'DIAMOND_AXE'
    data: 0
    nome: '&eLenhador'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &ccortar @meta madeiras'
      - '&7Quests: &e@quests'
      - ''

  fazendeiro:
    slot: 22
    skull:
      ativado: false
      url: ''
    material: 'DIAMOND_HOE'
    data: 0
    nome: '&eFazendeiro'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &ccolher @meta plantas'
      - '&7Quests: &e@quests'
      - ''

  pescador:
    slot: 20
    skull:
      ativado: false
      url: ''
    material: 'FISHING_ROD'
    data: 0
    nome: '&ePescador'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &cpescar @meta peixes'
      - '&7Quests: &e@quests'
      - ''

  cacador:
    slot: 16
    skull:
      ativado: false
      url: ''
    material: 'BOW'
    data: 0
    nome: '&eCaçador'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &cmatar @meta monstros'
      - '&7Quests: &e@quests'
      - ''

  assassino:
    slot: 24
    skull:
      ativado: false
      url: ''
    material: 'DIAMOND_SWORD'
    data: 0
    nome: '&eAssassino'
    lore:
      - '&7Salário Inicial: &2R$ &f@salary'
      - '&7Meta: &casassinar @meta jogadores'
      - '&7Quests: &e@quests'
      - ''

  #   @player   : nome do jogador
  #   @job      : nome do emprego do jogador
  #   @current  : meta para receber o salário
  #   @meta     : meta para receber o salário
  #   @total    : total de blocos, peixes ou entidades quebradas, pescadas ou mortas.
  #   @salary   : salário do jogador
  #   @quests   : número de quests concluidas.
  info:
    slot: 36
    nome: '&e@player'
    lore:
      - '&7Emprego Atual: &f@job'
      - '&7Salário Atual: &2R$ &f@salary'
      - ''
      - '&7Meta: &a@current&7/&c@meta'
      - '&7Total: @total'
      - '&7Quests concluidas: &e@quests'

item-sair:
  slot: 44
  skull:
    ativado: false
    url: ''
  material: 'PAPER'
  data: 0
  nome: '&cPedir Demissão'
  lore:
    - '&7Se sair do seu emprego todo o seu'
    - '&7progresso conquistado será perdido!'

item-confirmar:
  skull:
    ativado: true
    url: 'http://textures.minecraft.net/texture/8d82fcfa5578715c0d248e0aac42ab572e9a826ed3dad9dc66c9926e8473ed'
  material: 'WOOL'
  data: 5
  nome: '&aConfirmar'
  lore:
    - '&7Clique para confirmar'

item-cancelar:
  skull:
    ativado: true
    url: 'http://textures.minecraft.net/texture/bc2e972afa9115b6d32075b1f1b7fed7aa29a5341c1024288361abe8e69b46'
  material: 'WOOL'
  data: 14
  nome: '&cCancelar'
  lore:
    - '&7Clique para cancelar'

# irá aparecer no gui das quests
desafio-completado: '&eDesafio concluído'
desafio-iniciado: '&cDesafio não concluído'
desafio-bloqueado: '&cDesafio bloqueado'
```

### API

- [JobAPI](https://github.com/SrBlecaute01/SrEmpregos/blob/master/src/main/java/br/com/blecaute/jobs/apis/JobAPI.java)

### Eventos

- `JobContractEvent` - Chamado quando um jogador é contratado para um emprego.
- `JobDismissEvent` - Chamado quando um jogador pede demissão do emprego.
- `JobGetPaymentEvent` - Chamado quando o jogador recebe seu pagamento.
- `JobQuestCompleteEvent` - Chamado quando o jogador completa uma quest.
