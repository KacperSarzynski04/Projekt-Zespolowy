
const express = require('express');
const app = express();

app.use(express.static('./dist/app-frontend'));

app.get('/*', (req, res) =>
  res.sendFile('index.html', {root: 'dist/app-frontend/'}),
);

app.listen(process.env.PORT || 8081);
