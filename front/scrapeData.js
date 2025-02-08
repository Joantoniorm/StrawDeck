import fetch from 'node-fetch';
import * as cheerio from 'cheerio';

import fs from 'fs';

const url = 'https://en.onepiece-cardgame.com/cardlist/?series=569108';

async function scrapeCardData() {
    try {
        const response = await fetch(url);
        const body = await response.text();
        const $ = cheerio.load(body);
        const cards = [];

        $('dl.modalCol').each((index, element) => {
            const cardNumber = $(element).find('.infoCol span').first().text().trim();
            const cardName = $(element).find('.cardName').text().trim();
            const cardImage = $(element).find('.frontCol img').attr('data-src').split('?')[0];
            const cardCost = $(element).find('.cost').text().replace('Cost', '').trim();
            const cardPower = $(element).find('.power').text().replace('Power', '').trim();
            const cardCounter = $(element).find('.counter').text().replace('Counter', '').trim();
            const cardColor = $(element).find('.color').text().replace('Color', '').trim();
            const cardType = $(element).find('.feature').text().replace('Type', '').trim();
            const cardEffect = $(element).find('.text').text().replace('Effect', '').trim();
            const cardSet = $(element).find('.getInfo').text().replace('Card Set(s)', '').trim();

            cards.push({
                number: cardNumber,
                name: cardName,
                image: cardImage,
                cost: cardCost,
                power: cardPower,
                counter: cardCounter,
                color: cardColor,
                type: cardType,
                effect: cardEffect,
                set: cardSet
            });
        });

        fs.writeFileSync('cardData.json', JSON.stringify(cards, null, 2));
        console.log('Datos de las cartas guardados exitosamente en cardData.json');
    } catch (error) {
        console.error(`Error al hacer scraping: ${error.message}`);
    }
}

scrapeCardData();