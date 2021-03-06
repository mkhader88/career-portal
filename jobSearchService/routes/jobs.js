require("dotenv").config();
const express = require("express");
const router = express.Router();
const { Client } = require('@elastic/elasticsearch');
const { query } = require("express");
//const client = new Client({ node: process.env.ES_ADDRESS })

const client = new Client({
    cloud: { id: 'career-portal:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvOjQ0MyQ2YjYxNmZiNTRlNmU0ZDI0OWU1NDViMzdlMTZiMDQzZCQzYWEzNzNmODRlY2M0ZDU3OWE3YTE5Y2ZiYTRjNzQ1OQ==' },
    auth: {
        username: 'elastic',
        password: 'gnT3Y120zNSWIkxN3JRWRjNS'
    }
})

router.get("/", async (req, res) => {
    await client.indices.refresh({ index: process.env.ELASTICINDEX })
    let query = { index: process.env.ELASTICINDEX }
    if(req.query.job) query.q = `*${req.query.job}*`;
    await client.search(query).then(response => {
        return res.status(200).json({
            jobs: response.body.hits.hits
        })
    });
});

// router.get('/:id', async (req, res) => {
//     let query = { index: process.env.ELASTICINDEX, id: req.params.id }
//     await client.get(query).then(response => res.send(response.body));
// });

// router.get('/', async (req, res) => {
//     await client.indices.refresh({ index: process.env.ELASTICINDEX })
//     await client.search({
//         index: process.env.ELASTICINDEX,
//         q: req.query.q
//     }).then(response => {
//         return res.status(200).json({
//             jobs: response.body.hits.hits
//         })
//         //res.send(response)
//     });
// });

module.exports = router;